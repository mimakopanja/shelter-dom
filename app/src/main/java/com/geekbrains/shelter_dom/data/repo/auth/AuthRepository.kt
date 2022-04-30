package com.geekbrains.shelter_dom.data.repo.auth

import com.geekbrains.shelter_dom.data.model.auth.AuthResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface AuthRepository {
    fun authorization(email: String?, password: CharSequence): Single<AuthResponse>
}