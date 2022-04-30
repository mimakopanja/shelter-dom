package com.geekbrains.shelter_dom.data.repo.auth

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.model.auth.AuthResponse
import com.geekbrains.shelter_dom.data.model.pet.Breed
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call

class AuthRepositoryImpl(
    private val authApi: PetsApi
) : AuthRepository {

    override fun authorization(email: String?, password: CharSequence): Single<AuthResponse> =
        authApi.authorization(email, password).flatMap { response ->
            Single.just(response)
        }.subscribeOn(Schedulers.io()) as Single<AuthResponse>
}