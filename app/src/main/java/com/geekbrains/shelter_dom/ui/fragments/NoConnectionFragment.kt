package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.databinding.FragmentNoConnectionBinding


class NoConnectionFragment: Fragment() {

    companion object{
        fun newInstance() = NoConnectionFragment()
    }

    private lateinit var binding: FragmentNoConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoConnectionBinding.inflate(inflater, container, false)
        binding.animationViewIcon.setAnimation(R.raw.cat)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}