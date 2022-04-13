package com.geekbrains.shelter_dom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.databinding.BottomSheetFilterDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterDialog : BottomSheetDialogFragment() {
    companion object {
        fun newInstance() = FilterDialog()
    }

    private lateinit var binding: BottomSheetFilterDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}