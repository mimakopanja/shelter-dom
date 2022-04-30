package com.geekbrains.shelter_dom.data.model.auth

import android.os.Parcelable
import com.geekbrains.shelter_dom.data.model.user.Profile
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    val created_at: String? = "",
    val email: String? = "",
    val email_verified_at: @RawValue Any = "",
    val id: Int? = null,
    val is_admin: Int? = null,
    val name: String? = "",
    val updated_at: String? = "",
    val isLoggedIn: Boolean? = null
): Parcelable