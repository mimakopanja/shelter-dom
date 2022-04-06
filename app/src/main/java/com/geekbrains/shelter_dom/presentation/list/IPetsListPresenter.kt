package com.geekbrains.shelter_dom.presentation.list

import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.presentation.pets.PetItemView

interface IPetsListPresenter: IListPresenter<PetItemView> {
    fun setFilterPetsBySubstring(substring: String)
}