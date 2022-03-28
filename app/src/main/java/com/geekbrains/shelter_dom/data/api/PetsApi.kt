package com.geekbrains.shelter_dom.data.api

import com.geekbrains.shelter_dom.data.pet.Pet
import retrofit2.http.GET
import retrofit2.http.Path

interface PetsApi {
    @GET("/catalog")
    fun fetchPets(): List<Pet>

    @GET("/catalog/{id}")
    fun fetchPetById(@Path("id") id: String): Pet

}