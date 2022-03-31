package com.geekbrains.shelter_dom.presentation.pets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.data.pet.Data
import com.geekbrains.shelter_dom.databinding.ItemPetBinding

class PetsAdapter(
    private val data: List<Data>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    private lateinit var binding: ItemPetBinding

    class PetsViewHolder(
        private val viewBinding: ItemPetBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(
            data: Data,
            onClickListener: OnClickListener
        ) {
            with(viewBinding) {


//                itemImageView.setImageResource()
                itemTextView.text = data.name
                itemView.setOnClickListener {
                    onClickListener.onClick(data)
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
        val data: Data = data[position]
        holder.bind(data, onClickListener)
    }

    override fun getItemCount() = data.size

    class OnClickListener(val clickListener: (data: Data) -> Unit) {
        fun onClick(data: Data) = clickListener(data)
    }
}