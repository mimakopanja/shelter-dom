package com.geekbrains.shelter_dom.ui.dialogs

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.DialogFragment
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.databinding.FragmentImagePopupBinding
import com.geekbrains.shelter_dom.utils.*
import com.google.android.material.tabs.TabLayoutMediator

class ImageDialog: DialogFragment() {

    private lateinit var binding: FragmentImagePopupBinding

    private var pet: Data = Data()
    private var darkStatusBar = false

    companion object {
        fun newInstance(pet: Data): ImageDialog {
            return ImageDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(PET_IMAGE_TAG, pet)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagePopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.overridePendingTransition(0, 0)

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(MainActivity(), true)
        }
        dialog?.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (darkStatusBar) {
            this.dialog?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        dialog?.setCanceledOnTouchOutside(true)
        pet = arguments?.getParcelable(PET_IMAGE_TAG) ?: Data()
        binding.viewPager.adapter = ViewPagerAdapter(pet)

        binding.dotsIndicator.setViewPager2(binding.viewPager)


        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor(COLOR_TRANSPARENT), ALPHA)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = ANIMATION_DURATION
        colorAnimation.addUpdateListener { animator ->
            binding.imagePopupLayout.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()


        binding.relative.alpha = 0f
        binding.relative.animate().alpha(1f).setDuration(ANIMATION_DURATION).setInterpolator(
            DecelerateInterpolator()
        ).start()

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor(COLOR_TRANSPARENT), ALPHA)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = ANIMATION_DURATION
        colorAnimation.addUpdateListener { animator ->
            binding.imagePopupLayout.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        binding.relative.animate().alpha(0f).setDuration(ANIMATION_DURATION).setInterpolator(
            DecelerateInterpolator()
        ).start()

        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                activity?.finish()
                activity?.overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags =
                winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }

}