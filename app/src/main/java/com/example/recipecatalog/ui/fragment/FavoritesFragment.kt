package com.example.recipecatalog.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipecatalog.R
import com.example.recipecatalog.ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_catalog) {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recipeAdapter = RecipeAdapter { recipe ->
            val action = FavoritesFragmentDirections.actionFavoriteFragmentToRecipeDetailFragment(recipe)
            findNavController().navigate(action)
        }

        recyclerView.adapter = recipeAdapter

        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipesMap ->
            val recipesList = recipesMap.values.toList()
            recipeAdapter.submitList(recipesList)
        }


        val progressBar: ProgressBar = view.findViewById(R.id.loadingProgressBar)

        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            progressBar.visibility = View.GONE
            if (!recipes.isEmpty()) {
                val recipesList = recipes.values.toList()
                recipeAdapter.submitList(recipesList)
            }
        }
        viewModel.loadFavorites()
    }
}
