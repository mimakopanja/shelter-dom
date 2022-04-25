package com.geekbrains.shelter_dom.ui.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.databinding.ImageViewPagerBinding
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.IMG_BASE_URL


class ViewPagerAdapter(pet: Data) :  RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>(){

    private val imagesArray = pet.images
    private lateinit var binding: ImageViewPagerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ImageViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(App.INSTANCE.applicationContext)
            .load(IMG_BASE_URL.plus(imagesArray?.get(position)?.path))
            .centerCrop()
            .into(holder.image);
    }

    override fun getItemCount(): Int = imagesArray?.size!!


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = binding.imageviewPopup
    }
}