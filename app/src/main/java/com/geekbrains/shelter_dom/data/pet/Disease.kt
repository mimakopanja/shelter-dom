package com.geekbrains.shelter_dom.data.pet

import com.google.gson.annotations.SerializedName

data class Disease(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)