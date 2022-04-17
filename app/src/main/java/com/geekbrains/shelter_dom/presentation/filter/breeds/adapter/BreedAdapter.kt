package com.geekbrains.shelter_dom.presentation.filter.breeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.data.pet.model.Breed
import com.geekbrains.shelter_dom.databinding.ItemFilterBinding
import com.geekbrains.shelter_dom.presentation.filter.breeds.BreedView
import com.geekbrains.shelter_dom.presentation.filter.breeds.IBreedListPresenter
import com.geekbrains.shelter_dom.presentation.filter.types.adapter.TypeAdapter
import kotlinx.android.extensions.LayoutContainer

class BreedAdapter(
    private val presenter: IBreedListPresenter
) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    private lateinit var binding: ItemFilterBinding

    class BreedViewHolder(
        private val viewBinding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, BreedView {
        override val containerView = viewBinding.root

        override fun loadBreed(data: Breed) {
            viewBinding.cbItemFilter.text = data.name
            viewBinding.cbItemFilter.isChecked = data.isChecked
        }

        override var pos = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        binding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        val holder = BreedViewHolder(binding)
        binding.cbItemFilter.setOnClickListener {
            presenter.itemClickListener?.invoke(holder)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

}