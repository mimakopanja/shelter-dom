package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.databinding.FragmentAboutBinding
import com.geekbrains.shelter_dom.utils.changeFragmentTitle

class AboutFragment : Fragment() {

    companion object{
        fun newInstance() = AboutFragment()
    }

    private lateinit var binding : FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeFragmentTitle(activity as MainActivity, getString(R.string.about_the_shelter), "")
    }

}