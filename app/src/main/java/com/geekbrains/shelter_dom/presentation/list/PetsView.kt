package com.geekbrains.shelter_dom.presentation.list

import com.geekbrains.shelter_dom.data.model.pet.Data
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(AddToEndSingleStrategy::class)
interface PetsView: MvpView{
    fun init()
    fun updateList()
    fun showProgress()
    fun hideProgress()
    fun noConnection()
    fun showError(message: String)

    fun updateTypes()
    fun updateBreeds()
    fun updateAges()

    @Skip
    fun openPetDetails(pet: Data)

    @Skip
    fun openSlider(pet: Data)
}