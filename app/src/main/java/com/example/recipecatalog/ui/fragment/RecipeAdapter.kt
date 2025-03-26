package com.example.recipecatalog.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipecatalog.R
import com.example.recipecatalog.model.Recipe

class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        holder.itemView.setOnClickListener { onRecipeClick(recipe) }
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeImageView: ImageView = itemView.findViewById(R.id.recipeImageView)
        private val recipeTitleTextView: TextView = itemView.findViewById(R.id.recipeDetailTitleTextView)
        private val recipeDescriptionTextView: TextView = itemView.findViewById(R.id.recipeDescriptionTextView)

        fun bind(recipe: Recipe) {
            recipeTitleTextView.text = recipe.title

            val descriptionBuilder = StringBuilder()

            if (recipe.timeEstimated > 0) {
                descriptionBuilder.append("${recipe.timeEstimated} мин")
            }

            if (recipe.ingredients.isNotBlank()) {
                if (descriptionBuilder.isNotEmpty()) {
                    descriptionBuilder.append(" • ")
                }
                descriptionBuilder.append(
                    recipe.ingredients.take(70).let {
                        if (it.length == 70) "$it..." else it
                    }
                )
            }

            if (descriptionBuilder.isEmpty() && recipe.category.isNotBlank()) {
                descriptionBuilder.append(recipe.category)
            }

            recipeDescriptionTextView.text = descriptionBuilder.toString()

            Glide.with(itemView.context)
                .load(recipe.imageUrl.ifEmpty { R.drawable.ic_recipe_placeholder })
                .placeholder(R.drawable.ic_recipe_placeholder)
                .into(recipeImageView)
        }
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
}