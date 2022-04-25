package com.geekbrains.shelter_dom.data.repo

import com.geekbrains.shelter_dom.data.model.pet.Breed
import com.geekbrains.shelter_dom.data.model.pet.Pet
import com.geekbrains.shelter_dom.data.model.pet.Type
import io.reactivex.rxjava3.core.Single

interface PetsRepository {
    fun getPets(
        typeName: String,
        breedName: String,
        agePos: String,
        parasitesState: String,
        name: String,
        page: Int
    ): Single<Pet>?
    fun getBreeds(): Single<ArrayList<Breed>>
    fun getTypes(): Single<ArrayList<Type>>
}