package com.geekbrains.shelter_dom.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.R

const val SPLASH_DISPLAY_LENGTH: Long = 1000;
class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadApp()
    }

    private fun loadApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, SPLASH_DISPLAY_LENGTH)
    }

    companion object{
        fun newInstance() = SplashActivity()
    }
}