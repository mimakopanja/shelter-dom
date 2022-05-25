package com.geekbrains.shelter_dom.data.repo.pets

import com.geekbrains.shelter_dom.data.model.pet.Breed
import com.geekbrains.shelter_dom.data.model.pet.Pet
import com.geekbrains.shelter_dom.data.model.pet.Type
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response

interface PetsRepository {
    fun getPets(
        token: String,
        typeName: String,
        breedName: String,
        agePos: String,
        parasitesState: String,
        name: String,
        page: Int
    ): Single<Pet>?

    fun getFavPets(
        token: String
    ): Single<Pet>?

    fun deleteFromFavorites(token: String?, id: Int?, callback: Callback<Unit>)
    fun addToFavourites(token: String?, id: Int?, callback: Callback<Unit>)

    fun getBreeds(): Single<ArrayList<Breed>>
    fun getTypes(): Single<ArrayList<Type>>
}