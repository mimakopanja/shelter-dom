package com.geekbrains.shelter_dom

import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet


class PetViewModel(
    val name: String,
    val image: String,
) {

    object Mapper {
        fun map(pet: Data) =
            PetViewModel(
                pet.name,
                pet.images.first().path
            )
    }
}