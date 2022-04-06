package com.geekbrains.shelter_dom.data.pet.repo

import com.geekbrains.shelter_dom.data.api.NetworkStatus
import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PetRepositoryImpl(
    private val petsApi: PetsApi
) : PetRepository {

    override fun getPets(): Single<Pet> =
            petsApi.fetchPets().subscribeOn(Schedulers.io())


    override fun getPetById(id: String): Single<Data> =
        petsApi
            .fetchPetById(id)
            .subscribeOn(Schedulers.io())
}