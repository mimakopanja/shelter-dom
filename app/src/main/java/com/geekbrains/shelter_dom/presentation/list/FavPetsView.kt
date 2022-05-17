package com.geekbrains.shelter_dom.presentation.list

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavPetsView : MvpView {
    fun init()
    fun updateList()
    fun noConnection()
    fun showError(message: String)
    fun hideProgress()
    fun deleteFav(id: Int?, position: Int)
    fun showEmptyMessage()
}