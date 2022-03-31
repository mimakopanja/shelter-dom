package com.geekbrains.shelter_dom.data.pet

import retrofit2.Call
import retrofit2.Callback

interface PetRepository {
    fun getPets(callback: Callback<Base>)

    fun getPetById(id: String): Call<Data>
}