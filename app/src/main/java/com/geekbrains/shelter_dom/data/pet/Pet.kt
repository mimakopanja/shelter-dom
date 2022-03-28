package com.geekbrains.shelter_dom.data.pet

import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("id") val id : Int,
    @SerializedName("type") val type : String,
    @SerializedName("breed") val breed : String,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("birthday_at") val birthday_at : String,
    @SerializedName("images") val images : List<String>,
    @SerializedName("inoculations") val inoculations : List<String>,
    @SerializedName("diseases") val diseases : List<String>,
    @SerializedName("parasites") val parasites : List<String>
)