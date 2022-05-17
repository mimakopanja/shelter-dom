package com.geekbrains.shelter_dom.ui.fragments

import android.animation.Animator
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.util.MalformedJsonException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.dx.dxloadingbutton.lib.AnimationType
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.model.auth.User
import com.geekbrains.shelter_dom.data.repo.auth.AuthRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentAuthBinding
import com.geekbrains.shelter_dom.presentation.auth.AuthPresenter
import com.geekbrains.shelter_dom.presentation.auth.AuthView
import com.geekbrains.shelter_dom.ui.Screens
import com.geekbrains.shelter_dom.utils.*
import com.shashank.sony.fancytoastlib.FancyToast
import com.validator.easychecker.EasyChecker
import com.validator.easychecker.exceptions.DeveloperErrorException
import com.validator.easychecker.exceptions.InputErrorException
import com.validator.easychecker.util.PasswordPattern
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import kotlin.math.log


class AuthFragment : MvpAppCompatFragment(), AuthView {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private val presenter by moxyPresenter {
        AuthPresenter(
            AuthRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerForm.visibility = View.GONE
        binding.loginForm.visibility = View.VISIBLE
        binding.loginAnimation.setAnimation(R.raw.login)


        binding.goToRegistration.onCLick {
            setVisibility(binding.loginForm, false)
            setVisibility(binding.registerForm, true)
        }

        binding.buttonLogin.onCLick { login() }
        binding.buttonCreateAccount.onCLick { onCreateAccBtnClick() }
    }

    private fun onCreateAccBtnClick() {
        try {
            val isValidationSuccess = EasyChecker.validateInput(
                requireContext(),
                8,
                PasswordPattern.PASSWORD_PATTERN_NONE,
                binding.registerName,
                binding.registerEmail,
                binding.registerPassword,
                binding.registerPassword2
            )

            if (isValidationSuccess) {
                presenter.register(
                    binding.registerName.text.toString(),
                    binding.registerEmail.text.toString(),
                    binding.registerPassword.text.toString(),
                    binding.registerPassword2.text.toString()
                )
            } else {
                showError("Not validated")
            }
        } catch (developerErrorException: DeveloperErrorException) {
            developerErrorException.printStackTrace()
        } catch (inputErrorException: InputErrorException) {
            inputErrorException.message?.let { showError(it) }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun login() {
        val email: String = binding.loginName.text.toString().trim()
        val pass: String = binding.loginPassword.text.toString().trim()
        presenter.authorize(email, pass.toString())
    }

    private fun isEnabled(button: Button, boolean: Boolean) {
        button.isEnabled = boolean
    }

    override fun showError(msg: String) {
        binding.buttonLogin.loadingFailed()
        customToast(requireContext(), msg, FancyToast.ERROR)
    }

    override fun showProgress() {
        binding.buttonLogin.animationEndAction = {
            nextScreen()
            Unit
        }
        binding.buttonLogin.loadingSuccessful()
    }

    override fun buttonLogin() {
        binding.buttonLogin.startLoading()
    }

    override fun nextScreen() = with(binding) {
        val cx = (buttonLogin.left + buttonLogin.right) / 2
        val cy = (buttonLogin.top + buttonLogin.bottom) / 2

        val animator = ViewAnimationUtils.createCircularReveal(
            animateView,
            cx,
            cy,
            0f,
            resources.displayMetrics.heightPixels * 1.2f
        )
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        animateView.visibility = View.VISIBLE
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                buttonLogin.postDelayed({
                    buttonLogin.reset()
                    animateView.visibility = View.INVISIBLE
                }, 200)
            }

            override fun onAnimationEnd(animation: Animator) {
                App.INSTANCE.router.navigateTo(Screens.OpenMainActivity())
                requireActivity().overridePendingTransition(0, 0)
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    override fun successRegistration() {
        setVisibility(binding.registerForm, false)
        setVisibility(binding.loginForm, true)
    }
}