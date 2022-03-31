package com.geekbrains.shelter_dom.data.pet

import com.google.gson.annotations.SerializedName


data class Base (
	@SerializedName("data") val data : List<Data> = arrayListOf()
)