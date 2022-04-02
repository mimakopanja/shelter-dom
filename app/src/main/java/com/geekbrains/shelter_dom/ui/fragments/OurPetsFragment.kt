package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.geekbrains.shelter_dom.App
import com.geekbrains.shelter_dom.data.pet.PetRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentOurPetsBinding
import com.geekbrains.shelter_dom.presentation.pets.PetsPresenter
import com.geekbrains.shelter_dom.presentation.pets.PetsView
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class OurPetsFragment : MvpAppCompatFragment(), PetsView {

    companion object {
        fun newInstance() = OurPetsFragment()
    }

    private lateinit var binding: FragmentOurPetsBinding

    private val presenter by moxyPresenter {
        PetsPresenter(PetRepositoryImpl(), App.INSTANCE.router)
    }

    var adapter: PetsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurPetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun init() {
        adapter = PetsAdapter(requireContext(), presenter.data, presenter.petClickListener)
        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter!!.isHeader(position)) manager.spanCount else 1
            }
        }
        binding.rvPets.layoutManager = manager
        binding.rvPets.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun updateList() {
        adapter?.updateData(presenter.data)
    }

    override fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

}