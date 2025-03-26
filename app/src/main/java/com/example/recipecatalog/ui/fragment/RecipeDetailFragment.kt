package com.example.recipecatalog.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.recipecatalog.R
import com.example.recipecatalog.model.Recipe
import com.example.recipecatalog.ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var likeButton: ImageButton
    private lateinit var recipe: Recipe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = arguments?.getParcelable("recipe") ?: return
        likeButton = view.findViewById(R.id.likeButton)

        val recipeTitleTextView: TextView = view.findViewById(R.id.recipeDetailTitleTextView)
        val recipeIngredientsTextView: TextView = view.findViewById(R.id.recipeDetailIngredientsTextView)
        val recipeInstructionsTextView: TextView = view.findViewById(R.id.recipeDetailInstructionsTextView)
        val recipeCategoryTextView: TextView = view.findViewById(R.id.recipeDetailCategoryTextView)
        val recipeTimeTextView: TextView = view.findViewById(R.id.recipeDetailTimeTextView)

        val viewPager: ViewPager2 = view.findViewById(R.id.recipeImageViewPager)

        recipeTitleTextView.text = recipe.title
        recipeIngredientsTextView.text = recipe.ingredients
        recipeInstructionsTextView.text = recipe.instructions
        recipeCategoryTextView.text = recipe.category
        recipeTimeTextView.text = recipe.timeEstimated.toString()


        updateLikeIcon(recipe.isFavorite)

        val imageAdapter = RecipeImageAdapter(recipe.images)
        viewPager.adapter = imageAdapter

        val backButton: ImageButton = view.findViewById(R.id.backToCatalogButton)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.setInitialFavoriteState(recipe.isFavorite)

        viewModel.favoriteState.observe(viewLifecycleOwner) { isFavorite ->
            updateLikeIcon(isFavorite)
        }

        likeButton.setOnClickListener {
            viewModel.toggleFavorite(recipe)
        }
    }

    private fun updateLikeIcon(isFavorite: Boolean) {
        val iconRes = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_empty
        val colorRes = if (isFavorite) R.color.colorActiveIcon else R.color.colorInactiveIcon

        likeButton.setImageResource(iconRes)
        likeButton.setColorFilter(ContextCompat.getColor(requireContext(), colorRes))
    }

}
