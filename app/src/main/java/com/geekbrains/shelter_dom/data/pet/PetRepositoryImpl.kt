package com.geekbrains.shelter_dom.data.pet

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.api.PetsApiFactory

class PetRepositoryImpl(
    private val petsApi: PetsApi = PetsApiFactory.create()
) : PetRepository {

    override fun getPets(): List<Pet> =
        petsApi.fetchPets()

    override fun getPetById(id: String): Pet =
        petsApi.fetchPetById(id)

}