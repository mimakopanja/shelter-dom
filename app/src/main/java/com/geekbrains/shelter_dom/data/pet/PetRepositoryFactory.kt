package com.geekbrains.shelter_dom.data.pet

object PetRepositoryFactory {
    fun create(): PetRepository = PetRepositoryImpl()
}