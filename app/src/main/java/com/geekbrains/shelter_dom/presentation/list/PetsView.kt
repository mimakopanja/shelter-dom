package com.geekbrains.shelter_dom.presentation.list

import com.geekbrains.shelter_dom.data.pet.model.Data
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
    fun showInternetConnection()
    fun showSnack(message: String)
    fun showError(message: Throwable)
    fun scrollList(currentItem: Int)

    @Skip
    fun openPetDetails(pet:Data)
}