package com.geekbrains.shelter_dom.presentation.fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.databinding.ItemFavoriteBinding
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.pets.PetItemView
import com.geekbrains.shelter_dom.utils.*
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer

class FavAdapter(
    private val presenter: IPetsListPresenter
) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    private lateinit var binding: ItemFavoriteBinding
    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        val holder = FavAdapter.FavViewHolder(binding)

        return holder
    }

    class FavViewHolder(
        private val viewBinding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, PetItemView {

        override val containerView = viewBinding.root

        private lateinit var petItem: Data

        override fun loadPet(pet: Data) = with(viewBinding) {
            petItem = pet
            favoritePetName.text = pet.name
            favoritePetBreed.text = pet.breed
            favoritePetAge.text = calculateAge(pet.birthday_at)
            favoritePetDiseases.text = pet.disease?.first()?.name
            favoritePetInoculations.text = pet.inoculation?.first()?.name
            favoritePetParasites.text = pet.treatment_of_parasites
            favoritePetType.text = pet.type

            Picasso.get()
                .load(IMG_BASE_URL.plus(pet.images?.first()?.path))
                .placeholder(R.drawable.ic_placeholder_dog)
                .fit()
                .centerCrop()
                .into(favoriteImageView)
        }

        override var pos = -1
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
        lastPosition = holder.bindingAdapterPosition

    }
}