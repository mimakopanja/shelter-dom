package com.geekbrains.shelter_dom.presentation.user

import com.geekbrains.shelter_dom.data.model.user.UserSingle
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserView: MvpView {
    fun loadUsers(user: UserSingle)
    fun hideProgress()
    fun showProgress()
    fun showError(msg: String)
}