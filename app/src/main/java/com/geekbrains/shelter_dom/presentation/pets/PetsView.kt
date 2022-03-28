package com.geekbrains.shelter_dom.presentation.pets

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PetsView : MvpView {
    fun init()
    fun updateList()
}