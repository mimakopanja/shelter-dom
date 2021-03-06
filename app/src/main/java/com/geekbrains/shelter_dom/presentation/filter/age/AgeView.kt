package com.geekbrains.shelter_dom.presentation.filter.age

import com.geekbrains.shelter_dom.data.model.pet.AgeState
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AgeView : IAgeView {
    fun loadAge(data: AgeState)
}