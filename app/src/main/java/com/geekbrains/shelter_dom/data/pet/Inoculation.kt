package com.geekbrains.shelter_dom.data.pet

import com.google.gson.annotations.SerializedName

data class Inoculation(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)
