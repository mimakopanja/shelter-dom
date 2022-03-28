package com.geekbrains.shelter_dom.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PetsApiFactory {
    fun create(): PetsApi =
        Retrofit.Builder()
            .baseUrl("https://localhost:8000/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PetsApi::class.java)
}