package com.geekbrains.shelter_dom.presentation.pets


import android.util.Log
import android.widget.Toast
import com.geekbrains.shelter_dom.IMG_BASE_URL
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import com.geekbrains.shelter_dom.data.pet.repo.PetRepository
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class PetsPresenter(
    private val petsRepo: PetRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<PetsView>() {

    val petListPresenter = PetsListPresenter()

    class PetsListPresenter : IPetsListPresenter {

        val pets = mutableListOf<Data>()
        override var itemClickListener: ((PetItemView) -> Unit)? = null

        override fun bindView(view: PetItemView) {
            pets[view.pos].let { pet ->
                view.showName(pet.name)
                view.loadImage(IMG_BASE_URL.plus(pet.images.first().path))
            }
        }

        override fun getCount() = pets.size

        fun setPets(list: List<Data>) {
            pets.clear()
            pets.addAll(list)
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        startLoading()
        viewState.init()

    }

    private fun startLoading() {
        petsRepo.getPets()
            .observeOn(uiScheduler)
            .subscribe({ pets ->
                pets.data?.let { petListPresenter.setPets(it) }
                viewState.updateList()
            }, { error -> viewState.showError(error) })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

