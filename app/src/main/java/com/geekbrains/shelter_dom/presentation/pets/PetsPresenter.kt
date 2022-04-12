package com.geekbrains.shelter_dom.presentation.pets


import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.repo.PetRepository
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.utils.NETWORK_EXCEPTIONS
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.util.*

class PetsPresenter(
    private val petsRepo: PetRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<PetsView>() {

    val petListPresenter = PetsListPresenter()
    private var disposables = CompositeDisposable()

    class PetsListPresenter : IPetsListPresenter {

        val pets = mutableListOf<Data>()
        private val fullPets = mutableListOf<Data>()

        override fun setFilterPetsBySubstring(substring: String) {

            val filteredList: MutableList<Data> = ArrayList()
            if (substring.isEmpty()) {
                filteredList.addAll(fullPets)
            } else {
                val filterPattern = substring.lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in fullPets) {
                    if (item.name?.lowercase(Locale.getDefault())
                            ?.contains(filterPattern) == true
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            setFilterPets(filteredList)
        }

        private fun setFilterPets(filteredList: MutableList<Data>) {
            pets.clear()
            pets.addAll(filteredList)
        }

        override var itemClickListener: ((PetItemView) -> Unit)? = null

        override fun bindView(view: PetItemView) {
            pets[view.pos].let { pet ->
                view.loadPet(pet)
            }
        }

        override fun getCount() = pets.size

        fun setPets(list: List<Data>) {
            pets.clear()
            pets.addAll(list)
            fullPets.addAll(list)
        }

        var currentItem: Int = 0
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        startLoading(1)
        viewState.init()

        petListPresenter.itemClickListener = { itemView ->
            petListPresenter.currentItem = itemView.pos
            val pet = petListPresenter.pets[itemView.pos]
            viewState.openPetDetails(pet)
        }

    }

    fun startLoading(page: Int) {
        petsRepo.getPets(page)
            ?.retry(3)
            ?.subscribeOn(uiScheduler)
            ?.doOnError { viewState.showInternetConnection() }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe { viewState.showProgress() }
            ?.doFinally{ viewState.hideProgress()}
            ?.subscribe(
                {
                    petListPresenter.pets.addAll(it!!)
                    viewState.updateList()
                }, {
                    viewState.showError(it)
                    viewState.showInternetConnection()
                }
            )?.let {
                disposables.add(it)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun onViewCreated() {
        viewState.scrollList(petListPresenter.currentItem)
    }
}