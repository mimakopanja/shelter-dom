package com.geekbrains.shelter_dom.data.repo.auth

import com.geekbrains.shelter_dom.data.model.auth.AuthAndRegisterResponse
import io.reactivex.rxjava3.core.Single

interface AuthRepository {

    fun authorization(email: String?, password: CharSequence): Single<AuthAndRegisterResponse>

    fun registration(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Single<AuthAndRegisterResponse>
}