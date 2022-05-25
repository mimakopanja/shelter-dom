package com.geekbrains.shelter_dom.presentation.pets


import com.geekbrains.shelter_dom.data.model.pet.*
import com.geekbrains.shelter_dom.data.repo.pets.PetsRepository
import com.geekbrains.shelter_dom.presentation.filter.age.AgeView
import com.geekbrains.shelter_dom.presentation.filter.age.IAgeListPresenter
import com.geekbrains.shelter_dom.presentation.filter.breeds.BreedView
import com.geekbrains.shelter_dom.presentation.filter.breeds.IBreedListPresenter
import com.geekbrains.shelter_dom.presentation.filter.types.ITypeListPresenter
import com.geekbrains.shelter_dom.presentation.filter.types.TypeView
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.utils.*
import com.geekbrains.shelter_dom.utils.exceptions.ConnectionException
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetsPresenter(
    private val filterRepo: PetsRepository,
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

    val meta = mutableListOf<Meta>()

    private var disposables = CompositeDisposable()

    class PetsListPresenter : IPetsListPresenter {

        val pets = mutableListOf<Data>()

        override var itemClickListener: ((PetItemView) -> Unit)? = null
        override var onLongClickListener: ((PetItemView) -> Unit)? = null
        override var favClickListener: ((PetItemView) -> Unit)? = null

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
        override var onLongClickListener: ((BreedView) -> Unit)? = null
        override var favClickListener: ((BreedView) -> Unit)? = null


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
        override var onLongClickListener: ((TypeView) -> Unit)? = null
        override var favClickListener: ((TypeView) -> Unit)? = null

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
        override var onLongClickListener: ((AgeView) -> Unit)? = null
        override var favClickListener: ((AgeView) -> Unit)? = null

        override fun bindView(view: AgeView) {
            ages[view.pos].let { ageString ->
                view.loadAge(ageString)
            }
        }

        override fun getCount() = ages.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        startLoading()
        startTypesLoading()
        startBreedsLoading()
        startAgeLoading()
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

        petListPresenter.onLongClickListener = { itemView ->
            petListPresenter.currentItem = itemView.pos
            val pet = petListPresenter.pets[itemView.pos]
            viewState.openSlider(pet)
        }

        petListPresenter.favClickListener = { itemView ->
            petListPresenter.currentItem = itemView.pos
            val pet = petListPresenter.pets[itemView.pos]

            if (pet.favourite == false) {
                filterRepo.addToFavourites(
                    "Bearer ${SharedPrefManager.getInstance().token}",
                    pet.id,
                    callback = object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            petListPresenter.setPets(mutableListOf())
                            petListPresenter.currentPage = 1
                            startLoading()
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            viewState.showError("Error adding to favorites!")
                        }

                    })
            } else {
                filterRepo.deleteFromFavorites(
                    "Bearer ${SharedPrefManager.getInstance().token}",
                    pet.id,
                    callback = object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            petListPresenter.setPets(mutableListOf())
                            petListPresenter.currentPage = 1
                            startLoading()
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            viewState.showError("ERROR")
                        }
                    })
            }
        }
    }

    fun startLoading() {
        try {
            filterRepo.getPets(
                "Bearer ${SharedPrefManager.getInstance().token}",
                filterType,
                filterBreed,
                filterAgeState,
                filterParasites,
                searchName,
                petListPresenter.currentPage
            )
                ?.retry(5)
                ?.subscribeOn(uiScheduler)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doFinally { viewState.hideProgress() }
                ?.subscribe(
                    {
                        petListPresenter.pets.addAll(it.data!!)
                        meta.addAll(listOf(it.meta))
                        viewState.updateList()
                    }, {
//                        viewState.noConnection()
//                        viewState.showError(it.toString())
                    }
                )?.let {
                    disposables.add(it)
                }
        } catch (e: Exception) {
            viewState.showError(e.message)
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

    fun nextPage() {
        while (petListPresenter.currentPage <= meta.first().last_page!!) {
            petListPresenter.currentPage++
            startLoading()
        }
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