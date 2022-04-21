package com.geekbrains.shelter_dom.presentation.pets


import com.geekbrains.shelter_dom.data.pet.model.AgeState
import com.geekbrains.shelter_dom.data.pet.model.Breed
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Type
import com.geekbrains.shelter_dom.data.pet.repo.FilterRepository
import com.geekbrains.shelter_dom.data.pet.repo.PetRepository
import com.geekbrains.shelter_dom.presentation.filter.age.AgeView
import com.geekbrains.shelter_dom.presentation.filter.age.IAgeListPresenter
import com.geekbrains.shelter_dom.presentation.filter.breeds.BreedView
import com.geekbrains.shelter_dom.presentation.filter.breeds.IBreedListPresenter
import com.geekbrains.shelter_dom.presentation.filter.types.ITypeListPresenter
import com.geekbrains.shelter_dom.presentation.filter.types.TypeView
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.ageStrings
import com.geekbrains.shelter_dom.utils.exceptions.ApiExceptions
import com.geekbrains.shelter_dom.utils.exceptions.ConnectionException
import com.geekbrains.shelter_dom.utils.isConnected
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.util.*

class PetsPresenter(
    private val petsRepo: PetRepository,
    private val filterRepo: FilterRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<PetsView>() {

    val petListPresenter = PetsListPresenter()
    val typeListPresenter = TypeListPresenter()
    val breedListPresenter = BreedListPresenter()
    val ageListPresenter = AgeListPresenter()

    private var filterType = ""
    private var filterBreed = ""
    private var filterAgeState = ""
    private var filterParasites = ""
    private var searchName = ""

    private var disposables = CompositeDisposable()

    class PetsListPresenter : IPetsListPresenter {

        val pets = mutableListOf<Data>()

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
        }

        var currentItem: Int = 0
        var currentPage: Int = 1
    }

    class BreedListPresenter : IBreedListPresenter {
        val breeds = mutableListOf<Breed>()

        override var itemClickListener: ((BreedView) -> Unit)? = null

        override fun bindView(view: BreedView) {
            breeds[view.pos].let { breed ->
                view.loadBreed(breed)
            }
        }

        override fun getCount() = breeds.size
    }

    class TypeListPresenter : ITypeListPresenter {
        val types = mutableListOf<Type>()

        override var itemClickListener: ((TypeView) -> Unit)? = null

        override fun bindView(view: TypeView) {
            types[view.pos].let { type ->
                view.loadType(type)
            }
        }

        override fun getCount() = types.size
    }

    class AgeListPresenter : IAgeListPresenter {
        val ages = mutableListOf<AgeState>()

        override var itemClickListener: ((AgeView) -> Unit)? = null

        override fun bindView(view: AgeView) {
            ages[view.pos].let { ageString ->
                view.loadAge(ageString)
            }
        }

        override fun getCount() = ages.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if (!isConnected(App.INSTANCE.applicationContext)) {
            viewState.noConnection()
        } else {
            startLoading()
            startTypesLoading()
            startBreedsLoading()
            startAgeLoading()
        }
        viewState.init()

        typeListPresenter.itemClickListener = { itemView ->
            typeListPresenter.types[itemView.pos].isChecked =
                !typeListPresenter.types[itemView.pos].isChecked
        }

        breedListPresenter.itemClickListener = { itemView ->
            breedListPresenter.breeds[itemView.pos].isChecked =
                !breedListPresenter.breeds[itemView.pos].isChecked
        }

        ageListPresenter.itemClickListener = { itemView ->
            ageListPresenter.ages[itemView.pos].isChecked =
                !ageListPresenter.ages[itemView.pos].isChecked
        }

        petListPresenter.itemClickListener = { itemView ->
            petListPresenter.currentItem = itemView.pos
            val pet = petListPresenter.pets[itemView.pos]
            viewState.openPetDetails(pet)
        }

    }

    private fun startLoading() {
        try {
            filterRepo.getFilteredPets(
                filterType,
                filterBreed,
                filterAgeState,
                filterParasites,
                searchName,
                petListPresenter.currentPage
            )
                ?.retry(3)
                ?.subscribeOn(uiScheduler)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe { viewState.showProgress() }
                ?.doFinally { viewState.hideProgress() }
                ?.subscribe(
                    {
                            petListPresenter.pets.addAll(it!!)
                            viewState.updateList()
                    }, {
//                        viewState.noConnection()
                        viewState.showError(it.toString())
                    }
                )?.let {
                    disposables.add(it)
                }
        } catch (e: ApiExceptions) {
            if (e.errorCode == 504) {
                viewState.showSnack("Error")
            } else {
                    viewState.showError(e.message)
            }
        }
    }

    private fun startTypesLoading() {
        try {
            filterRepo.getTypes()
                .subscribeOn(uiScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        typeListPresenter.types.addAll(it)
                        viewState.updateTypes()
                    }, {
                        viewState.noConnection()
                        viewState.showError(it.toString())
                    }
                ).let {
                    disposables.add(it)
                }
        } catch (e: ConnectionException) {
            viewState.noConnection()
            viewState.showSnack(e.message)
        }
    }

    private fun startBreedsLoading() {
        try {
            filterRepo.getBreeds()
                .subscribeOn(uiScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        breedListPresenter.breeds.addAll(it)
                        viewState.updateBreeds()
                    }, {
                        viewState.noConnection()
                        viewState.showError(it.toString())
                    }
                ).let {
                    disposables.add(it)
                }
        } catch (e: ConnectionException) {
            viewState.noConnection()
            viewState.showSnack(e.message)
        }
    }

    private fun startAgeLoading() {
        ageListPresenter.ages.addAll(ageStrings())
        viewState.updateAges()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun nextPage() {
        petListPresenter.currentPage++
        startLoading()
    }

    fun loadFilteredPets() {
        filterType = typeListPresenter.types.find { it.isChecked }?.name ?: ""
        filterBreed = breedListPresenter.breeds.find { it.isChecked }?.name ?: ""
        ageListPresenter.ages.find { it.isChecked }?.apply {
            filterAgeState = (ageListPresenter.ages.indexOf(this) + 1).toString()
        }
        petListPresenter.setPets(mutableListOf())
        petListPresenter.currentPage = 1
        startLoading()
    }

    fun resetFilter() {
        filterType = ""
        filterAgeState = ""
        filterParasites = ""
        typeListPresenter.types.forEach { it.isChecked = false }
        breedListPresenter.breeds.forEach { it.isChecked = false }
        ageListPresenter.ages.forEach { it.isChecked = false }
    }

    fun setParasites(state: String) {
        filterParasites = state
    }

    fun searchByString(s: String) {
        searchName = s
        petListPresenter.setPets(mutableListOf())
        petListPresenter.currentPage = 1
        startLoading()
    }
}