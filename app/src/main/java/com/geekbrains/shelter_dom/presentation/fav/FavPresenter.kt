package com.geekbrains.shelter_dom.presentation.fav

import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.data.repo.pets.PetsRepository
import com.geekbrains.shelter_dom.presentation.list.FavPetsView
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.pets.PetItemView
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.SharedPrefManager
import com.geekbrains.shelter_dom.utils.exceptions.ApiExceptions
import com.geekbrains.shelter_dom.utils.isConnected
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavPresenter(
    private val repo: PetsRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<FavPetsView>() {

    val favListPresenter = FavListPresenter()
    private var disposables = CompositeDisposable()

    class FavListPresenter : IPetsListPresenter {

        val pets = mutableListOf<Data>()

        override var itemClickListener: ((PetItemView) -> Unit)? = null
        override var favClickListener: ((PetItemView) -> Unit)? = null
        override var onLongClickListener: ((PetItemView) -> Unit)? = null

        fun getId(position: Int): Int? {
            return pets[position].id
        }

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
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (!isConnected(App.INSTANCE.applicationContext)) {
            viewState.noConnection()
        } else {
            startLoading()
        }
        viewState.init()
    }

    fun startLoading() {
        try {
            repo.getFavPets(
                "Bearer ${SharedPrefManager.getInstance().token}"
            )
                ?.retry(5)
                ?.subscribeOn(uiScheduler)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doFinally { viewState.hideProgress() }
                ?.subscribe(
                    {
                        if (it.data.isNullOrEmpty()) {
                            viewState.showEmptyMessage()
                        }

                        favListPresenter.pets.clear()
                        favListPresenter.pets.addAll(it.data!!)
                        viewState.updateList()
                    }, {
                        viewState.showError("You Are Not Logged In!")
                    }
                )?.let {
                    disposables.add(it)
                }
        } catch (e: ApiExceptions) {
            viewState.showError(e.message)
        }
    }

    fun removeFavPet(index: Int?) {
        repo.deleteFromFavorites(
            "Bearer ${SharedPrefManager.getInstance().token}",
            index,
            callback = object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    favListPresenter.setPets(mutableListOf())
                    startLoading()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    viewState.showError("ERROR")
                }
            })
    }
}