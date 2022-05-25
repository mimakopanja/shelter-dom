package com.geekbrains.shelter_dom.presentation.filter.breeds

import com.geekbrains.shelter_dom.data.model.pet.Breed
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface BreedView : IBreedView {
    fun loadBreed(data: Breed)
}