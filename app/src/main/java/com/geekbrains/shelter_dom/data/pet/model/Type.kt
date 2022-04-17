package com.geekbrains.shelter_dom.data.pet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @Expose var isChecked: Boolean = false
)
