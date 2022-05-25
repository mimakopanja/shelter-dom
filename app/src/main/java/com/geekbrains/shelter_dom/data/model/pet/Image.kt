package com.geekbrains.shelter_dom.data.model.pet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val path: String
): Parcelable