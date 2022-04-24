package com.geekbrains.shelter_dom.presentation.pets


import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import com.geekbrains.shelter_dom.presentation.list.IPetView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PetItemView : IPetView {
    fun loadPet(pet: Data)
}