package com.geekbrains.shelter_dom.data.repo.users

import com.geekbrains.shelter_dom.data.model.user.UserSingle
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun getUsers(id: Int): Single<UserSingle>
}