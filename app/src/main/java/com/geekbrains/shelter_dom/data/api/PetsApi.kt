package com.geekbrains.shelter_dom.data.api


import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

interface PetsApi {

    @GET("animals")
    fun fetchPets(): Single<Pet>

    @GET("animals/{id}")
    fun fetchPetById(
        @Path("id") id: String
    ): Single<Data>
}