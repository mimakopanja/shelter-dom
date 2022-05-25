package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.model.user.Data
import com.geekbrains.shelter_dom.data.model.user.Profile
import com.geekbrains.shelter_dom.databinding.FragmentUserUpdateBinding
import com.geekbrains.shelter_dom.utils.IMG_BASE_URL_AVATAR

class UserUpdateFragment : Fragment() {

    companion object {
        fun newInstance(user: Profile?): UserUpdateFragment {
            return UserUpdateFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("USER", user)
                }
            }
        }
    }

    private lateinit var binding: FragmentUserUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile= arguments?.getParcelable("USER")  ?: Profile()

        binding.profileName.setText(profile.name)
        binding.profilePhone.setText(profile.phone)

        Glide.with(requireContext())
            .load(IMG_BASE_URL_AVATAR.plus(profile.avatar))
            .placeholder(R.drawable.ic_account)
            .circleCrop()
            .into(binding.ivUserUpdate)
    }
}