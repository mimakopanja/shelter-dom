package com.geekbrains.shelter_dom.data.repo.pets

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.model.pet.Breed
import com.geekbrains.shelter_dom.data.model.pet.Pet
import com.geekbrains.shelter_dom.data.model.pet.Type
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Callback

class PetsRepositoryImpl(
    private val petsApi: PetsApi
) : PetsRepository {

    override fun getPets(
        token: String,
        typeName: String,
        breedName: String,
        agePos: String,
        parasitesState: String,
        name: String,
        page: Int
    ): Single<Pet>? =
        petsApi.fetchPets(token, typeName, breedName, agePos, parasitesState, name, page)
            .flatMap { response ->
                Single.just(response)
            }?.subscribeOn(Schedulers.io())

    override fun getFavPets(token: String): Single<Pet>? =
        petsApi.fetchFavPets(token)
            .flatMap { response ->
                Single.just(response)
            }?.subscribeOn(Schedulers.io())

    override fun getBreeds(): Single<ArrayList<Breed>> =
        petsApi.fetchBreeds().flatMap { response ->
            Single.just(response.data)
        }.subscribeOn(Schedulers.io()) as Single<ArrayList<Breed>>

    override fun getTypes(): Single<ArrayList<Type>> =
        petsApi.fetchTypes().flatMap { response ->
            Single.just(response.data)
        }.subscribeOn(Schedulers.io()) as Single<ArrayList<Type>>

    override fun deleteFromFavorites(token: String?, id: Int?, callback: Callback<Unit>) {
        petsApi.deletePetFromFavourites(token, id).enqueue(callback)
    }

    override fun addToFavourites(token: String?, id: Int?, callback: Callback<Unit>) {
        petsApi.addPetToFavourites(token, id).enqueue(callback)
    }

}