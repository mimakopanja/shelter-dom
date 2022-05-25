package com.geekbrains.shelter_dom.data.model.pet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Inoculation(
    val id: Int,
    val name: String
): Parcelable