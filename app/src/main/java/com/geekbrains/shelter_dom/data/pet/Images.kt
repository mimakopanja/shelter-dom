package com.geekbrains.shelter_dom.data.pet

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("path") var path: String? = null

)
