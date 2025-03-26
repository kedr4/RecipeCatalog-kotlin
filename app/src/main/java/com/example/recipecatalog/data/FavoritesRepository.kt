package com.example.recipecatalog.data

import com.example.recipecatalog.data.db.FavoritesDataSource
import com.example.recipecatalog.data.db.UserDataSource
import com.example.recipecatalog.model.Recipe
import javax.inject.Inject

data class FavoritesRepository @Inject constructor(
    private val dataSource: FavoritesDataSource,
    private val userDataSource: UserDataSource
) {

    suspend fun getFavoriteRecipes(): Map<String, Recipe> {
        val userId = userDataSource.getCurrentUserId()
        return if (userId != null) {
            val recipes = dataSource.getFavorites(userId)

            val updatedRecipes = recipes.map { recipe ->
                recipe.copy(isFavorite = true)
            }

            updatedRecipes.associateBy { it.id }
        } else {
            emptyMap()
        }
    }

    suspend fun addFavorite(recipe: Recipe) {
        val userId = userDataSource.getCurrentUserId()
        if (userId != null) {
            dataSource.addFavorite(userId, recipe)
        }
    }

    suspend fun removeFavorite(recipeId: String) {
        val userId = userDataSource.getCurrentUserId()
        if (userId != null) {
            dataSource.removeFavorite(userId, recipeId)
        }
    }
}


