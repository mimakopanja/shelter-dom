package com.geekbrains.shelter_dom.data.repo.users

import com.geekbrains.shelter_dom.data.api.PetsApi
import com.geekbrains.shelter_dom.data.model.user.UserSingle
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepositoryImpl(
    private val api: PetsApi
) : UserRepository {

    override fun getUsers(id: Int): Single<UserSingle> =
        api.getUsers(id).flatMap { response ->
            Single.just(response)
        }.subscribeOn(Schedulers.io()) as Single<UserSingle>
}