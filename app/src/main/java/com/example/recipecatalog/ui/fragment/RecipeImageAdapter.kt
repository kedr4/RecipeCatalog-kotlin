package com.example.recipecatalog.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipecatalog.R
import com.bumptech.glide.Glide

class RecipeImageAdapter(private val images: List<String>) : RecyclerView.Adapter<RecipeImageAdapter.RecipeImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_image, parent, false)
        return RecipeImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeImageViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(images[position])
            .placeholder(R.drawable.ic_recipe_placeholder)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size

    inner class RecipeImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.recipeImageView)
    }
}