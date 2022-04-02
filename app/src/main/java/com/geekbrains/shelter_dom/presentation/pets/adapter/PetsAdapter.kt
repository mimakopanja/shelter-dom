package com.geekbrains.shelter_dom.presentation.pets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.geekbrains.shelter_dom.App
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.pet.Data
import com.geekbrains.shelter_dom.databinding.ItemPetBinding
import com.geekbrains.shelter_dom.databinding.ItemPetHeaderBinding


class PetsAdapter(
    private val context: Context,
    private var data: List<Data>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    private lateinit var binding: ItemPetBinding

    class PetsViewHolder(
        private val viewBinding: ItemPetBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(
            data: Data,
            onClickListener: OnClickListener
        ) {
            with(viewBinding) {

                val options: RequestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_rabbit)
                    .centerCrop()


                val imageUrl = App.INSTANCE.baseUrl + data.images[0].path
                Glide
                    .with(context)
                    .load(imageUrl)
                    .apply(options)
                    .into(itemImageView)
//                itemImageView.setImageResource()
                itemTextView.text = data.name
                itemView.setOnClickListener {
                    onClickListener.onClick(data)
                }
            }
        }
    }

    class HeaderHolder(
        private val viewBinding: ItemPetHeaderBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType === ITEM_VIEW_TYPE_HEADER) {
            return HeaderHolder(ItemPetHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        return PetsViewHolder(
            ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeader(position)) {
            return
        }

        val data: Data = data[position - 1]
        (holder as PetsViewHolder).bind(data, onClickListener)
    }

    override fun getItemCount() = data.size + 1

    fun updateData(newData: List<Data>) {
        data = newData
        notifyDataSetChanged()
    }

    fun isHeader(position: Int): Boolean = (position == 0)

    class OnClickListener(val clickListener: (data: Data) -> Unit) {
        fun onClick(data: Data) = clickListener(data)
    }
}