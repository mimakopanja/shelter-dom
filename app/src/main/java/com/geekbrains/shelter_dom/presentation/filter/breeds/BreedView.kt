package com.geekbrains.shelter_dom.presentation.filter.breeds

import com.geekbrains.shelter_dom.data.pet.model.Breed
import com.geekbrains.shelter_dom.data.pet.model.Type
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface BreedView : IBreedView {
    fun loadBreed(data: Breed)
}