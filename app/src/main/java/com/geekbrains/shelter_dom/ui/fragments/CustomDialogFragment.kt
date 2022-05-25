package com.geekbrains.shelter_dom.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.geekbrains.shelter_dom.databinding.CustomDialogFragmentBinding


class CustomDialogFragment: DialogFragment() {

    private lateinit var binding: CustomDialogFragmentBinding

    companion object {
        fun newInstance(bundle: Bundle): CustomDialogFragment {
            val fragment = CustomDialogFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
    }
}

