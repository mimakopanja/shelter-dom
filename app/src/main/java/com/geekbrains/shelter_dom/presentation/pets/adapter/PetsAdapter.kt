package com.geekbrains.shelter_dom.presentation.pets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.data.pet.Pet
import com.geekbrains.shelter_dom.databinding.ItemPetBinding

class PetsAdapter(
    private val pets: List<Pet>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    private lateinit var binding: ItemPetBinding

    class PetsViewHolder(
        private val viewBinding: ItemPetBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(
            pet: Pet,
            onClickListener: OnClickListener
        ) {
            with(viewBinding) {


//                itemImageView.setImageResource()
                itemTextView.text = pet.name
                itemView.setOnClickListener {
                    onClickListener.onClick(pet)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        return PetsViewHolder(
            ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        val pet: Pet = pets[position]
        holder.bind(pet, onClickListener)
    }

    override fun getItemCount() = pets.size

    class OnClickListener(val clickListener: (pet: Pet) -> Unit) {
        fun onClick(pet: Pet) = clickListener(pet)
    }
}