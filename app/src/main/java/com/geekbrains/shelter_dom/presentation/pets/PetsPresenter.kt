package com.geekbrains.shelter_dom.presentation.pets

import com.geekbrains.shelter_dom.data.pet.Pet
import com.geekbrains.shelter_dom.data.pet.PetRepository
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import com.geekbrains.shelter_dom.ui.Screens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class PetsPresenter(
    private val petsRepo: PetRepository,
    private val router: Router)
    : MvpPresenter<PetsView>() {

    lateinit var pets: List<Pet>

    val petClickListener = PetsAdapter.OnClickListener {
//        router.navigateTo(Screens.OpenPetDetailsFragment)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        viewState.init()
    }

    private fun loadData() {
        pets = petsRepo.getPets()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}