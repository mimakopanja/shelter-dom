package com.geekbrains.shelter_dom.presentation.user

import com.geekbrains.shelter_dom.data.model.user.UserSingle
import com.geekbrains.shelter_dom.data.repo.users.UserRepository
import com.geekbrains.shelter_dom.utils.exceptions.ApiExceptions
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class UserPresenter(
    private val userRepo: UserRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
): MvpPresenter<UserView>() {

    var user = UserSingle()

    fun getUsers(id: Int) {
         try {
             userRepo.getUsers(id!!)
                 .retry(5)
                 .subscribeOn(uiScheduler)
                 .observeOn(AndroidSchedulers.mainThread())
                 .doFinally { viewState.hideProgress() }
                 .subscribe(
                     {
                         user = it
                         viewState.loadUsers(user)
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