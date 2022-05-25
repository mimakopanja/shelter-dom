package com.geekbrains.shelter_dom.data.repo.auth

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.model.auth.AuthAndRegisterResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthRepositoryImpl(
    private val authApi: PetsApi
) : AuthRepository {

    override fun authorization(
        email: String?,
        password: CharSequence
    ): Single<AuthAndRegisterResponse> =
        authApi.authorization(email, password).flatMap { response ->
            Single.just(response)
        }.subscribeOn(Schedulers.io()) as Single<AuthAndRegisterResponse>

    override fun registration(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Single<AuthAndRegisterResponse> =
        authApi.registration(name, email, password, passwordConfirm).flatMap { response ->
            Single.just(response)
        }.subscribeOn(Schedulers.io()) as Single<AuthAndRegisterResponse>
}