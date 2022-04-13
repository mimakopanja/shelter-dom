package com.geekbrains.shelter_dom.data.pet.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geekbrains.shelter_dom.utils.DATABASE_TABLE_NAME

@Entity(tableName = DATABASE_TABLE_NAME)
data class PetEntity (
    @PrimaryKey
    val pet_id: Int,
    val pet_name: String? = "",
    val image_path: List<Image>? = listOf()
    )