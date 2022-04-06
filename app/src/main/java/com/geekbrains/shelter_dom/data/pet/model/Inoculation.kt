package com.geekbrains.shelter_dom.data.pet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Inoculation(
    val id: Int,
    val name: String
): Parcelable