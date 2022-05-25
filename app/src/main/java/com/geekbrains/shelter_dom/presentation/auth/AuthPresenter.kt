package com.geekbrains.shelter_dom.presentation.auth

import android.os.Handler
import android.os.Looper
import com.geekbrains.shelter_dom.data.model.auth.AuthAndRegisterResponse
import com.geekbrains.shelter_dom.data.repo.auth.AuthRepository
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.SharedPrefManager
import com.geekbrains.shelter_dom.utils.customToast
import com.geekbrains.shelter_dom.utils.exceptions.ApiExceptions
import com.github.terrakok.cicerone.Router
import com.shashank.sony.fancytoastlib.FancyToast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter


class AuthPresenter(
    private val authRepo: AuthRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<AuthView>() {

    var data = AuthAndRegisterResponse()

    fun authorize(login: String, password: String) {
        viewState.buttonLogin()
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
                        SharedPrefManager.getInstance().saveToken(it.token)
                        customToast(
                            App.INSTANCE.applicationContext,
                            "Welcome! You are successfully logged in!",
                            FancyToast.SUCCESS
                        )
                    }, {
                        viewState.showError("Email and password doesn't match!!")
                    }
                )
        } catch (e: ApiExceptions) {
            e.printStackTrace()
            viewState.showError("System error occurred! Please check your internet connection!")
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        try {
            authRepo.registration(name, email, password, confirmPassword)
                .retry(5)
                .subscribeOn(uiScheduler)
                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally { viewState.showProgress() }
                .subscribe(
                    {
                        viewState.showError("Welcome! You have successfully registered!")
                        viewState.successRegistration()
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