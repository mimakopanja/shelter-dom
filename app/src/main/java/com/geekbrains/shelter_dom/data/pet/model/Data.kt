package com.geekbrains.shelter_dom.data.pet.model

data class Data(
    val birthday_at: String,
    val breed: String,
    val description: String,
    val disease: List<Disease>,
    val id: Int,
    val images: List<Image>,
    val inoculation: List<Inoculation>,
    val name: String,
    val treatment_of_parasites: String,
    val type: String
)