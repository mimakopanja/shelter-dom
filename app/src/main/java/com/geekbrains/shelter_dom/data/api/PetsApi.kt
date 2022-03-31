package com.geekbrains.shelter_dom.data.api

import com.geekbrains.shelter_dom.data.pet.Base
import com.geekbrains.shelter_dom.data.pet.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PetsApi {
    @GET("/animals")
    fun fetchPets(): Call<Base>

    @GET("/animals/{id}")
    fun fetchPetById(@Path("id") id: String): Call<Data>

}