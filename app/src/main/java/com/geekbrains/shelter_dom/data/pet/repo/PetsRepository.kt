package com.geekbrains.shelter_dom.data.pet.repo

import com.geekbrains.shelter_dom.data.pet.model.*
import io.reactivex.rxjava3.core.Single

interface PetsRepository {
    fun getPets(
        typeName: String,
        breedName: String,
        agePos: String,
        parasitesState: String,
        page: Int
    ): Single<Pet>?
    fun getBreeds(): Single<ArrayList<Breed>>
    fun getTypes(): Single<ArrayList<Type>>
}