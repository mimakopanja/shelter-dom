package com.geekbrains.shelter_dom.data.pet.repo

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.pet.model.Breed
import com.geekbrains.shelter_dom.data.pet.model.Pet
import com.geekbrains.shelter_dom.data.pet.model.Type
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PetsRepositoryImpl(
    private val petsApi: PetsApi
): PetsRepository {
    override fun getPets(
        typeName: String,
        breedName: String,
        agePos: String,
        parasitesState: String,
        page: Int
    ): Single<Pet>? =
        petsApi.fetchPets(typeName, breedName, agePos, parasitesState, page)
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

}