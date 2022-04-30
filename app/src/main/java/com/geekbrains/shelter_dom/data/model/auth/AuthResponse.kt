package com.geekbrains.shelter_dom.data.model.auth

data class AuthResponse(
    val token: String? = null,
    val user: User? = null
)