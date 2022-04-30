package com.geekbrains.shelter_dom.data.model.pet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Data(
    val birthday_at: String? = "",
    val breed: String? = "",
    val description: String? = "",
    val disease: @RawValue List<Disease>? = listOf(),
    val id: Int? = 0,
    val images: @RawValue List<Image>? = listOf(),
    val inoculation: @RawValue List<Inoculation>? = listOf(),
    val name: String? = "",
    val treatment_of_parasites: String? = "",
    val type: String? = ""
): Parcelable