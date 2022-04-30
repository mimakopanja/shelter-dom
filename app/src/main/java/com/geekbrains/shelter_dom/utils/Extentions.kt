package com.geekbrains.shelter_dom.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.shelter_dom.data.model.pet.AgeState
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


fun formatTime(time: Long): String {
    val date = Date(time * 1000L)
    val sdf = SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())
    return sdf.format(date)
}

fun ageStrings() =
    arrayListOf(
        AgeState("< 1", false),
        AgeState("1 - 2", false),
        AgeState("2 - 5", false),
        AgeState(">= 5", false)

    )

fun View.onCLick(action: () -> Unit) {
    this.setOnClickListener {
            action()
        }
    }

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: ((View) -> Unit)? = null,
    length: Int = Snackbar.LENGTH_SHORT
) {
    val ourSnackBar = Snackbar.make(this, text, length)
    action?.let {
        ourSnackBar.setAction(actionText, it)
    }
    ourSnackBar.show()
}

@SuppressLint("ShowToast")
fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun setVisibility(view: View, isShown: Boolean) {
    view.visibility = if (isShown) View.VISIBLE else View.GONE
}
