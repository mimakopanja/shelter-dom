package com.geekbrains.shelter_dom.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.databinding.FragmentUserInfoBinding
import com.geekbrains.shelter_dom.ui.Screens
import com.geekbrains.shelter_dom.utils.App
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso


class UserFragment : Fragment() {

        companion object {
            fun newInstance() = UserFragment()
        }

        private lateinit var binding: FragmentUserInfoBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentUserInfoBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            Glide.with(requireContext())
                .load("https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80")
                .placeholder(R.drawable.ic_account)
                .circleCrop()
                .into(binding.ivUser)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_user_update -> {
                App.INSTANCE.router.navigateTo(Screens.OpenUserUpdateFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}