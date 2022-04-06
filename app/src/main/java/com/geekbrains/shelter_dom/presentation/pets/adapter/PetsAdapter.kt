package com.geekbrains.shelter_dom.presentation.pets.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.App
import com.geekbrains.shelter_dom.IMG_BASE_URL
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.databinding.ItemPetBinding
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.pets.PetItemView
import com.geekbrains.shelter_dom.ui.DialogPopup
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer


class PetsAdapter(
    private val presenter: IPetsListPresenter
) : RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    private lateinit var binding: ItemPetBinding

    class PetsViewHolder(
        private val viewBinding: ItemPetBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, PetItemView {
        override val containerView = viewBinding.root

        override fun loadPet(pet: Data) {

            viewBinding.itemTextView.text = pet.name

            Picasso.get()
                .load(IMG_BASE_URL.plus(pet.images?.first()?.path))
                .placeholder(R.drawable.ic_placeholder_dog)
                .fit()
                .centerCrop()
                .into(viewBinding.itemImageView)
        }
        override var pos = -1


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        return PetsViewHolder(
            ItemPetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }
    }

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount() = presenter.getCount()
}