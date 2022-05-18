package com.geekbrains.shelter_dom.data.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val address: String? = null,
    val avatar: String? = null,
    val birthday_at: String? = null,
    val description: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val surname: String? = null
): Parcelable