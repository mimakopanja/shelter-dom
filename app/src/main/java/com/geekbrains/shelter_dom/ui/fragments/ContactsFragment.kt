package com.geekbrains.shelter_dom.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.shelter_dom.databinding.FragmentContactsBinding
import com.geekbrains.shelter_dom.databinding.FragmentHelpBinding

@Suppress("DEPRECATION")
class ContactsFragment: Fragment() {

    companion object{
        fun newInstance() = ContactsFragment()
    }

    private lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.phoneTv.setOnClickListener {
            val phone = binding.phoneTv.text
            val intent: Intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            startActivity(intent)
        }
    }
}