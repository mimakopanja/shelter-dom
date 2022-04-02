package com.geekbrains.shelter_dom.data.api

import com.geekbrains.shelter_dom.data.pet.Base
import com.geekbrains.shelter_dom.data.pet.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PetsApi {
    @GET("api/animals")
    fun fetchPets(): Call<Base>

    @GET("api/animals/{id}")
    fun fetchPetById(@Path("id") id: String): Call<Data>

}