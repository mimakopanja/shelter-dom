package com.geekbrains.shelter_dom.data.pet

interface PetRepository {
    fun getPets(): List<Pet>

    fun getPetById(id: String): Pet
}