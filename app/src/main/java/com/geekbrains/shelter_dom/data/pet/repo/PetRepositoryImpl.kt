package com.geekbrains.shelter_dom.data.pet.repo

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.local.PetDatabase
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import com.geekbrains.shelter_dom.data.pet.model.PetEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

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

    override fun insertPet(pet: Data): Completable {
        return Completable.fromCallable {
            PetDatabase.petDatabase.petDao().insertPet(convertPetToEntity(pet))
        }
    }

    override fun removePetById(id: Int) {
        PetDatabase.petDatabase.petDao().deleteById(id)
    }


    override fun checkPetExistInDatabase(id: Int): Boolean {
        if (PetDatabase.petDatabase.petDao().petExistInDatabase(id)){
            return true
        }
        return false
    }

    private fun convertEntityToPet(petEntity: PetEntity): Data {
        return Data(
            id = petEntity.pet_id,
            name = petEntity.pet_name,
            images = petEntity.image_path
        )
    }

    private fun convertPetToEntity(pet: Data): PetEntity {
        return PetEntity(
            pet_id = pet.id ?: 0,
            pet_name = pet.name,
            image_path = pet.images
        )
    }
}