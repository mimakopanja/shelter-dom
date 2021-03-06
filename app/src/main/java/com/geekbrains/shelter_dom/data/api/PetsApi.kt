package com.geekbrains.shelter_dom.data.api


import com.geekbrains.shelter_dom.data.model.auth.AuthAndRegisterResponse
import com.geekbrains.shelter_dom.data.model.pet.BreedsData
import com.geekbrains.shelter_dom.data.model.pet.Pet
import com.geekbrains.shelter_dom.data.model.pet.TypesData
import com.geekbrains.shelter_dom.data.model.user.UserSingle
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface PetsApi {

    @GET("animals")
    fun fetchPets(
        @Header("Authorization") token: String,
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

    @GET("users/{id}")
    fun getUsers(
        @Path("id") id: Int
    ): Single<UserSingle>

    @POST("login")
    fun authorization(
        @Query("email") login: String?,
        @Query("password") pass: CharSequence?
    ): Single<AuthAndRegisterResponse>

    @POST("register")
    fun registration(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("password_confirmation") passwordConfirm: String
    ): Single<AuthAndRegisterResponse>

    @GET("favourites")
    fun fetchFavPets(
        @Header("Authorization") token: String
    ): Single<Pet>

    @POST("favourites")
    fun addPetToFavourites(
        @Header("Authorization") token: String?,
        @Query("id") id: Int?
    ):Call<Unit>


    @DELETE("favourites/{id}")
    fun deletePetFromFavourites(
        @Header("Authorization") token: String?,
        @Path("id") id: Int?
    ): Call<Unit>

}