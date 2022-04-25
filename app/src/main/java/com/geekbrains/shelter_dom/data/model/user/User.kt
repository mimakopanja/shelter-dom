package com.geekbrains.shelter_dom.data.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data")
    val data: List<Data>
)