package com.geekbrains.shelter_dom.presentation.filter.types.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.data.pet.model.Type
import com.geekbrains.shelter_dom.databinding.ItemFilterBinding
import com.geekbrains.shelter_dom.presentation.filter.types.ITypeListPresenter
import com.geekbrains.shelter_dom.presentation.filter.types.TypeView
import kotlinx.android.extensions.LayoutContainer

class TypeAdapter(
    private val presenter: ITypeListPresenter
) : RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {

    private lateinit var binding: ItemFilterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        binding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        val holder = TypeViewHolder(binding)
        binding.cbItemFilter.setOnClickListener {
            presenter.itemClickListener?.invoke(holder)
        }
        return holder
    }

    class TypeViewHolder(
        private val viewBinding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(viewBinding.root), LayoutContainer, TypeView {
        override val containerView = viewBinding.root

        override fun loadType(data: Type) {
            viewBinding.cbItemFilter.apply {
                text = data.name
                isChecked = data.isChecked
            }
        }

        override var pos = -1
    }


    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

}