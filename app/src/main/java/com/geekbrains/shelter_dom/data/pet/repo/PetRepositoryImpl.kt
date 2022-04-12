package com.geekbrains.shelter_dom.data.pet.repo

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.pet.model.Data
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PetRepositoryImpl(
    private val petsApi: PetsApi
) : PetRepository {

    override fun getPets(page: Int): Single<List<Data>?>? =
        petsApi.fetchPets(page).flatMap { response ->
            Single.just(response.data)
        }?.subscribeOn(Schedulers.io())

    override fun getPetById(id: String): Single<Data> =
        petsApi.fetchPetById(id).flatMap { response ->
            Single.just(response)
        }.subscribeOn(Schedulers.io())
}