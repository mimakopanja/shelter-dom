package com.geekbrains.shelter_dom

import android.app.Application
import android.widget.Toast
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller

class App : Application() {


    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            GoogleApiAvailability.getInstance().showErrorNotification(this, e.connectionStatusCode)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Toast.makeText(this, e.errorCode, Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        lateinit var INSTANCE: App
    }
}