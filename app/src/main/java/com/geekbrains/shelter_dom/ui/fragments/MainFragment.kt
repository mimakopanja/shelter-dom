package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.databinding.FragmentMainBinding
import com.geekbrains.shelter_dom.ui.Screens

class MainFragment : Fragment() {

    companion object{
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.makeFriendBtn.setOnClickListener {
            App.INSTANCE.router.replaceScreen(Screens.OpenOurPetsFragment())
        }
    }
}