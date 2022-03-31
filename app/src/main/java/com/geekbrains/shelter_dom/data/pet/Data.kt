package com.geekbrains.shelter_dom.data.pet

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("breed") var breed: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("treatment_of_parasites") var treatmentOfParasites: String? = null,
    @SerializedName("birthday_at") var birthdayAt: String? = null,
    @SerializedName("images") var images: ArrayList<Images> = arrayListOf(),
    @SerializedName("disease") var disease: ArrayList<Disease> = arrayListOf(),
    @SerializedName("inoculation") var inoculation: ArrayList<Inoculation> = arrayListOf()
)