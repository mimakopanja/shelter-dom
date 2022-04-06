package com.geekbrains.shelter_dom.presentation.list

import com.geekbrains.shelter_dom.data.api.NetworkStatus
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PetsView: MvpView{
    fun init()
    fun updateList()
    fun showError(message: Throwable)
}