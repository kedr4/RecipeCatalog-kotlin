package com.example.recipecatalog.data

import com.example.recipecatalog.data.db.RecipeDataSource
import com.example.recipecatalog.model.Recipe
import javax.inject.Inject

data class RecipeRepository @Inject constructor(
    private val dataSource: RecipeDataSource,
    private val favoritesRepository: FavoritesRepository
) {

    suspend fun getRecipes(): List<Recipe> {
        val recipes = dataSource.getRecipes()

        val favoriteRecipes = favoritesRepository.getFavoriteRecipes()

        return recipes.map { recipe ->
            recipe.copy(isFavorite = favoriteRecipes.contains(recipe.id))
        }
    }
}
