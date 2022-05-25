package com.geekbrains.shelter_dom.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.model.pet.AgeState
import com.google.android.material.snackbar.Snackbar
import com.shashank.sony.fancytoastlib.FancyToast
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

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

fun customToast(context: Context, msg: String, style: Int) {
    FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, style, false).show()
}

@SuppressLint("SimpleDateFormat")
fun calculateAge(date: String?): String {
    val currentDate = Calendar.getInstance()
    val myFormat = SimpleDateFormat("yyyy-MM-dd")
    var birthdate: Date? = null

    try {
        birthdate = date?.let { myFormat.parse(it) }
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val time = currentDate.time.time / 1000 - birthdate!!.time / 1000

    val years = time.toFloat().roundToInt() / 31536000
    val months = (time - years * 31536000).toFloat().roundToInt() / 2628000
    return if (years == 0) {
        months.toString().plus(" months")
    } else years.toString()
}

fun noConnectionDialog(activity: Activity){
    val mBottomSheetDialog = BottomSheetMaterialDialog.Builder(activity)
        .setTitle("Connection Problem")
        .setMessage("Checking the network cables, modem or router.\\n• Reconnecting to WI-FI")
        .setCancelable(false)
        .setPositiveButton(
            "Refresh", com.shashank.sony.fancytoastlib.R.drawable.ic_refresh_black_24dp
        ) { dialogInterface, which ->
            startActivity(App.INSTANCE.applicationContext, Intent(Settings.ACTION_WIFI_SETTINGS), null)
            dialogInterface.dismiss()
        }
        .setNegativeButton(
            "Cancel", com.google.android.material.R.drawable.mtrl_ic_cancel
        ) { dialogInterface, which ->
            Toast.makeText(App.INSTANCE.applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                .show()
            dialogInterface.dismiss()
        }
        .build()
    mBottomSheetDialog.show()
}