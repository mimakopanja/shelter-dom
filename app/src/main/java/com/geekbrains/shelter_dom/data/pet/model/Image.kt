package com.geekbrains.shelter_dom.data.pet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val path: String
): Parcelable