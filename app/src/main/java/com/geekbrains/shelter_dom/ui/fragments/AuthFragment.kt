package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
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

        binding.goToRegistration.setOnClickListener {
            binding.loginForm.visibility = View.GONE
            binding.registerForm.visibility = View.VISIBLE
        }
    }
}