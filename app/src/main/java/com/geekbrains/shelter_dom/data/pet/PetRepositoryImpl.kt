package com.geekbrains.shelter_dom.data.pet

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetRepositoryImpl(
    private val petsApi: PetsApi = PetsApiFactory.create()
) : PetRepository {

    override fun getPets(callback: Callback<Base>) {
        petsApi.fetchPets().enqueue(callback)
    }
    override fun getPetById(id: String): Call<Data> =
        petsApi.fetchPetById(id)

}