package com.geekbrains.shelter_dom.presentation.filter.types

import com.geekbrains.shelter_dom.data.model.pet.Type
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TypeView : ITypeView {
    fun loadType(data: Type)
}