package com.geekbrains.shelter_dom.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.databinding.CustomDialogFragmentBinding
import com.geekbrains.shelter_dom.utils.ALPHA
import com.geekbrains.shelter_dom.utils.ANIMATION_DURATION
import com.geekbrains.shelter_dom.utils.COLOR_TRANSPARENT
import com.geekbrains.shelter_dom.utils.PET_DETAIL_TAG

@Suppress("DEPRECATION")
class DialogPopup: AppCompatActivity() {

    private lateinit var binding: CustomDialogFragmentBinding

    private var pet: Data = Data()
    private var darkStatusBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogFragmentBinding.inflate(layoutInflater)
        overridePendingTransition(0, 0)
        setContentView(binding.root)

        val bundle = intent.extras
        pet = bundle?.getParcelable(PET_DETAIL_TAG)!!

        loadData(pet)
        

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (darkStatusBar) {
            this.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        this.window.statusBarColor = Color.TRANSPARENT
        setWindowFlag(this, false)

        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor(COLOR_TRANSPARENT), ALPHA)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = ANIMATION_DURATION
        colorAnimation.addUpdateListener { animator ->
            binding.dialogPopupWindow.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()


        binding.relative.alpha = 0f
        binding.relative.animate().alpha(1f).setDuration(ANIMATION_DURATION).setInterpolator(
            DecelerateInterpolator()
        ).start()

        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadData(pet: Data) {
        binding.petNameTv.text = pet.name
        binding.petDescTv.text = pet.description
        binding.petAgeTv.text = pet.birthday_at
        binding.petDiseasesTv.text = pet.disease?.first()?.name
        binding.petParasitesTv.text = pet.treatment_of_parasites.toString()
        binding.petBreedTv.text = pet.breed
        binding.petTypeTv.text = pet.type
        binding.petInoculationsTv.text = pet.inoculation?.first()?.name
    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }


    override fun onBackPressed() {
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor(COLOR_TRANSPARENT), ALPHA)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = ANIMATION_DURATION
        colorAnimation.addUpdateListener { animator ->
            binding.dialogPopupWindow.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        binding.relative.animate().alpha(0f).setDuration(ANIMATION_DURATION).setInterpolator(
            DecelerateInterpolator()
        ).start()

        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

}