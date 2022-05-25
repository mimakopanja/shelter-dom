package com.geekbrains.shelter_dom.presentation.auth

import com.geekbrains.shelter_dom.data.model.auth.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthView: MvpView {
    fun successRegistration()
    fun showProgress()
    fun showError(msg: String)
    fun nextScreen()
    fun buttonLogin()
}