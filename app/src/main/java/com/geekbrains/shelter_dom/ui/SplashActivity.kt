package com.geekbrains.shelter_dom.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.databinding.ActivityMainBinding
import com.geekbrains.shelter_dom.databinding.ActivitySplashBinding
import com.geekbrains.shelter_dom.databinding.FragmentOurPetsBinding
import com.geekbrains.shelter_dom.utils.SPLASH_DISPLAY_LENGTH

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.animationSplash.setAnimation(R.raw.splash)

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