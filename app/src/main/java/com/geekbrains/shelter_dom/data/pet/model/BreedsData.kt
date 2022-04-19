package com.geekbrains.shelter_dom.data.pet.model

import com.google.gson.annotations.SerializedName

data class BreedsData(
    @SerializedName("data") var data : ArrayList<Breed> = arrayListOf()
)
