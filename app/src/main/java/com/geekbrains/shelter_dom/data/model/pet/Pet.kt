package com.geekbrains.shelter_dom.data.model.pet

import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("data")
    val data: List<Data>? = listOf(),
    val links: Links,
    val meta: Meta
)