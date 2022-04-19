package com.geekbrains.shelter_dom.presentation.pets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.utils.IMG_BASE_URL
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.databinding.ItemPetBinding
import com.geekbrains.shelter_dom.presentation.filter.age.adapter.AgeAdapter
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.pets.PetItemView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer


class PetsAdapter(
    private val presenter: IPetsListPresenter
) : RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    private lateinit var binding: ItemPetBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
            binding = ItemPetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            /*          itemView.setOnClickListener {
                          presenter.itemClickListener?.invoke(this)
                      }*/

        val holder = PetsAdapter.PetsViewHolder(binding)
        binding.learnMoreButton.setOnClickListener {
            presenter.itemClickListener?.invoke(holder)
        }

        return holder
    }

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

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val results = FilterResults()
            presenter.setFilterPetsBySubstring(charSequence as String)
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            notifyDataSetChanged()
        }
    }

}