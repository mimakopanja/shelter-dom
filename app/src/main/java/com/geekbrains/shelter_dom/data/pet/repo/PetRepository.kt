package com.geekbrains.shelter_dom.data.pet.repo


import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.Pet
import io.reactivex.rxjava3.core.Single

interface PetRepository {
    fun getPets(page: Int): Single<List<Data>?>?
    fun getPetById(id: String): Single<Data>
}