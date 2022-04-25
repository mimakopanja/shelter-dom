package com.geekbrains.shelter_dom.data.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Data(
    val email: String,
    val name: String,
    val profile: @RawValue Profile
): Parcelable