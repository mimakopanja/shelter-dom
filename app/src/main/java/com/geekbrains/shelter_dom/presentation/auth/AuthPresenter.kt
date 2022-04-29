package com.geekbrains.shelter_dom.presentation.auth

import com.geekbrains.shelter_dom.data.model.auth.AuthResponse
import com.geekbrains.shelter_dom.data.repo.auth.AuthRepository
import com.geekbrains.shelter_dom.utils.SharedPrefManager
import com.geekbrains.shelter_dom.utils.exceptions.ApiExceptions
import com.geekbrains.shelter_dom.utils.showSnackBar
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter


class AuthPresenter(
    private val authRepo: AuthRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<AuthView>() {

    var data = AuthResponse()

    fun authorize(login: String, password: String) {
        if (login.isEmpty()) {
            viewState.showError("Login field is empty!")
        }
        if (password.isEmpty()) {
            viewState.showError("Password field is empty!")
        }
        if (login.isEmpty() || password.isEmpty()) {
            return
        }

        try {
            authRepo.authorization(login, password)
                .retry(5)
                .subscribeOn(uiScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.showProgress() }
                .subscribe(
                    {
                        data = it
                        SharedPrefManager.getInstance().saveUser(it.user)
                        viewState.showError("Welcome! You are successfully logged in!")
                        data.user?.let { it1 -> viewState.nextScreen(it1) }
                    }, {
                        viewState.showError(it.toString())
                    }
                )
        } catch (e: ApiExceptions) {
            e.printStackTrace()
            viewState.showError("System error occurred! Please check your internet connection!")
        }
    }
}