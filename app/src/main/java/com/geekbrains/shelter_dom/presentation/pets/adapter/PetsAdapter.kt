package com.geekbrains.shelter_dom.presentation.pets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.databinding.ItemPetBinding
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.pets.PetItemView
import com.geekbrains.shelter_dom.ui.fragments.FavoriteFragment
import com.geekbrains.shelter_dom.utils.*
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer


class PetsAdapter(
    private val presenter: IPetsListPresenter
) : RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    private lateinit var binding: ItemPetBinding
    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        binding = ItemPetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        val holder = PetsAdapter.PetsViewHolder(binding)

        binding.learnMoreButton.setOnClickListener {
            presenter.itemClickListener?.invoke(holder)
        }

        binding.itemImageView.setOnClickListener {
            presenter.onLongClickListener?.invoke(holder)
        }

        binding.favoriteButton.setOnClickListener{
            presenter.favClickListener?.invoke(holder)
        }

        return holder
    }

    class PetsViewHolder(
        private val viewBinding: ItemPetBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, PetItemView {

        override val containerView = viewBinding.root

        lateinit var petItem: Data

        override fun loadPet(pet: Data) {
            petItem = pet
            viewBinding.itemTextView.text = pet.name

            if (pet.favourite == true) {
                viewBinding.favoriteButton.setImageResource(R.drawable.ic_favorite)
            } else {viewBinding.favoriteButton.setImageResource(R.drawable.ic_favorite_border)}

            Picasso.get()
                .load(IMG_BASE_URL.plus(pet.images?.first()?.path))
                .placeholder(R.drawable.ic_placeholder_dog)
                .fit()
                .centerCrop()
                .into(viewBinding.itemImageView)
        }

        override var pos = -1
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {

        presenter.bindView(holder.apply { pos = position })
        lastPosition = holder.bindingAdapterPosition
    }
}