package com.geekbrains.shelter_dom.utils

import android.content.Context
import com.geekbrains.shelter_dom.data.model.auth.User

class SharedPrefManager() {

    val user: User
        get() {
            val sharedPreferences = App.INSTANCE.applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.getBoolean("IS_LOGGED_IN", false)
            return User(
                "",
                sharedPreferences.getString(SHARED_EMAIL, null),"",
                sharedPreferences.getInt(SHARED_ID, -1), -1, "","",
                sharedPreferences.getBoolean("IS_LOGGED_IN", false)
            )
        }


    fun saveUser(user: User?) {
        val sharedPreferences = App.INSTANCE.applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        user?.id?.let { editor.putInt(SHARED_ID, it) }
        editor.putString(SHARED_EMAIL, user?.email)
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.apply()

    }

    fun clear() {
        val sharedPreferences = App.INSTANCE.applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private var instance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(): SharedPrefManager {
            if (instance == null) {
                instance = SharedPrefManager()
            }
            return instance as SharedPrefManager
        }
    }
}