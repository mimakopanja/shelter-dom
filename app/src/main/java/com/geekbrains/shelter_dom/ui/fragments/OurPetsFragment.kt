package com.geekbrains.shelter_dom.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.shelter_dom.App
import com.geekbrains.shelter_dom.MY_ERROR
import com.geekbrains.shelter_dom.data.api.NetworkStatus
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.pet.repo.PetRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentOurPetsBinding
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.presentation.pets.PetsPresenter
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment

import moxy.ktx.moxyPresenter
import java.lang.Exception
import java.lang.Thread.sleep


class OurPetsFragment : MvpAppCompatFragment(), PetsView {

    companion object {
        fun newInstance() = OurPetsFragment()
    }

    private lateinit var binding: FragmentOurPetsBinding
    private var petId: Int = -1

    private val presenter by moxyPresenter {
        PetsPresenter(
            PetRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }


    private var adapter: PetsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurPetsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun init() {
        adapter = PetsAdapter(presenter.petListPresenter)
        binding.rvPets.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showError(message: Throwable) {
        Log.e(MY_ERROR, message.message ?: message.stackTraceToString())
    }


}