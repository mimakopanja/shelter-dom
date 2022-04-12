package com.geekbrains.shelter_dom.utils

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class App : Application() {

    val NETWORK_EXCEPTIONS = Arrays.asList<Class<*>>(
        UnknownHostException::class.java,
        SocketTimeoutException::class.java,
        ConnectException::class.java
    )

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        InternetUtils.init(this)
    }

    companion object{
        lateinit var INSTANCE: App
    }
}