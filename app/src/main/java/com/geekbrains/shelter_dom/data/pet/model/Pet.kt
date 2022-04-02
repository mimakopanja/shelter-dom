package com.geekbrains.shelter_dom.data.pet.model

import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("data")
    val data: List<Data>? = listOf()
)