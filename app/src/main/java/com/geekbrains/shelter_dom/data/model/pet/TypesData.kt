package com.geekbrains.shelter_dom.data.model.pet

import com.google.gson.annotations.SerializedName

data class TypesData(
    @SerializedName("data") var data: ArrayList<Type> = arrayListOf()
)
