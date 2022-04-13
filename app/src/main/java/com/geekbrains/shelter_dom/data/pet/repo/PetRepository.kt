package com.geekbrains.shelter_dom.data.pet.repo


import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface PetRepository {
    fun getPets(page: Int): Single<List<Data>?>?
    fun getPetById(id: String): Single<Data>
    fun insertPet(pet: Data): Completable
    fun removePetById(id: Int)
    fun checkPetExistInDatabase(id: Int): Boolean
}