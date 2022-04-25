package com.geekbrains.shelter_dom.data.model.pet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type_id") var typeId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @Expose var isChecked: Boolean = false
)
