package com.geekbrains.shelter_dom.data.api


import com.geekbrains.shelter_dom.data.pet.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PetsApi {

    @GET("animals/{id}")
    fun fetchPetById(
        @Path("id") id: String
    ): Single<Data>

    @GET("animals")
    fun fetchPets(
        @Query("type") typeName: String,
        @Query("breed") breedName: String,
        @Query("age") agePos: String,
        @Query("treatment_of_parasites") parasitesState: String,
        @Query("name") name: String,
        @Query("page") page: Int
    ): Single<Pet>

    @GET("breeds")
    fun fetchBreeds(): Single<BreedsData>

    @GET("types")
    fun fetchTypes(): Single<TypesData>
}