package com.geekbrains.shelter_dom.presentation.fav

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.databinding.ItemFavoriteBinding
import com.geekbrains.shelter_dom.presentation.fav.view.FavItemView
import com.geekbrains.shelter_dom.presentation.fav.view.IFavListPresenter
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import com.geekbrains.shelter_dom.utils.*
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer

class FavAdapter(
    private val presenter: IFavListPresenter
) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    private lateinit var binding: ItemFavoriteBinding
    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        val holder = FavAdapter.FavViewHolder(binding)


        binding.deleteIcon.setOnClickListener {
            presenter.itemClickListener?.invoke(holder)
        }

        return holder
    }

    class FavViewHolder(
        private val viewBinding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, FavItemView {

        override val containerView = viewBinding.root

        lateinit var petItem: Data

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

        val animation: Animation = AnimationUtils.loadAnimation(
            App.INSTANCE.applicationContext,
            if (position > lastPosition) {
                R.anim.scale_up
            } else R.anim.scale_up
        )

        holder.itemView.startAnimation(animation)
        lastPosition = holder.bindingAdapterPosition

    }
}