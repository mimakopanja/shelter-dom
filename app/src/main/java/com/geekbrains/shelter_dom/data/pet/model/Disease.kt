package com.geekbrains.shelter_dom.data.pet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    val id: Int,
    val name: String
): Parcelable