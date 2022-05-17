package com.geekbrains.shelter_dom.ui.fragments

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.data.repo.pets.PetsRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentFavoritesBinding
import com.geekbrains.shelter_dom.presentation.fav.FavAdapter
import com.geekbrains.shelter_dom.presentation.fav.FavPresenter
import com.geekbrains.shelter_dom.presentation.list.FavPetsView
import com.geekbrains.shelter_dom.utils.*
import com.shashank.sony.fancytoastlib.FancyToast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class FavoriteFragment : MvpAppCompatFragment(), FavPetsView {

    companion object {
        fun newInstance(token: String): FavoriteFragment {
            return FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString("USER_TOKEN", token)
                }
            }
        }
    }

    private val presenter by moxyPresenter {
        FavPresenter(
            PetsRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }
    private lateinit var binding: FragmentFavoritesBinding
    private var adapter: FavAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun init() {
        adapter = FavAdapter(presenter.petListPresenter)
        binding.favoriteRecyclerView.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun deleteFav(id: Int?, position: Int) {
        presenter.remove(id)
        adapter?.notifyItemRemoved(position)
    }

    override fun showEmptyMessage() {
        setVisibility(binding.emptyListLayout, true)
    }

    override fun noConnection() {
        customToast(requireContext(), "No Connection", FancyToast.ERROR)
    }

    override fun showError(message: String) {
        customToast(requireContext(), message, FancyToast.ERROR)
    }

    override fun hideProgress() {
        setVisibility(binding.progressBarLayout, false)
    }
}