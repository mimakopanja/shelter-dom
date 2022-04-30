package com.geekbrains.shelter_dom.presentation.filter.age.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.data.model.pet.AgeState
import com.geekbrains.shelter_dom.databinding.ItemFilterBinding
import com.geekbrains.shelter_dom.presentation.filter.age.AgeView
import com.geekbrains.shelter_dom.presentation.filter.age.IAgeListPresenter
import kotlinx.android.extensions.LayoutContainer

class AgeAdapter(
    private val presenter: IAgeListPresenter
) : RecyclerView.Adapter<AgeAdapter.AgeViewHolder>() {

    private lateinit var binding: ItemFilterBinding

    class AgeViewHolder(
        private val viewBinding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, AgeView {
        override val containerView = viewBinding.root

        override fun loadAge(data: AgeState) {
            viewBinding.cbItemFilter.text = data.ageString
            viewBinding.cbItemFilter.isChecked = data.isChecked
        }

        override var pos = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeViewHolder {
        binding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        val holder = AgeViewHolder(binding)
        binding.cbItemFilter.setOnClickListener {
            presenter.itemClickListener?.invoke(holder)
        }
        return holder
    }

    override fun onBindViewHolder(holder: AgeViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

}