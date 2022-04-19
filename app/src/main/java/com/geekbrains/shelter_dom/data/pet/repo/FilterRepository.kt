package com.geekbrains.shelter_dom.data.pet.repo

import com.geekbrains.shelter_dom.data.pet.model.Breed
import com.geekbrains.shelter_dom.data.pet.model.BreedsData
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Type
import io.reactivex.rxjava3.core.Single

interface FilterRepository {
    fun getFilteredPets(
        typeName: String,
        breedName: String,
        agePos: String,
        parasitesState: String,
        page: Int
    ): Single<List<Data>?>?
    fun getBreeds(): Single<ArrayList<Breed>>
    fun getTypes(): Single<ArrayList<Type>>
}