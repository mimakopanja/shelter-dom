package com.geekbrains.shelter_dom.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

val NETWORK_EXCEPTIONS = Arrays.asList<Class<*>>(
    UnknownHostException::class.java,
    SocketTimeoutException::class.java,
    ConnectException::class.java
)

fun formatTime (time:Long):String{
    val date = Date(time * 1000L)
    val sdf = SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())
    return sdf.format(date)
}

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: ((View) -> Unit)? = null,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    val ourSnackBar = Snackbar.make(this, text, length)
    action?.let {
        ourSnackBar.setAction(actionText, it)
    }
    ourSnackBar.show()
}

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting}
