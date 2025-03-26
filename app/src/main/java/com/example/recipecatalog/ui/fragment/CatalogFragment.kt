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
import com.example.recipecatalog.ui.viewmodel.CatalogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val viewModel: CatalogViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recipeAdapter = RecipeAdapter { recipe ->
            val action = CatalogFragmentDirections.actionCatalogFragmentToRecipeDetailFragment(recipe)
            findNavController().navigate(action)
        }

        recyclerView.adapter = recipeAdapter

        val progressBar: ProgressBar = view.findViewById(R.id.loadingProgressBar)

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            if (recipes.isEmpty()) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                recipeAdapter.submitList(recipes)
            }
        }

        viewModel.loadRecipes()
    }
}
