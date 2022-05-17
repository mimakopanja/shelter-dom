package com.geekbrains.shelter_dom.presentation.fav.view

import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.presentation.list.IPetView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavItemView : IPetView {
    fun loadPet(pet: Data)
}