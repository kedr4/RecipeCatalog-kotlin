package com.example.recipecatalog.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecatalog.data.FavoritesRepository
import com.example.recipecatalog.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _favoriteRecipes = MutableLiveData<Map<String, Recipe>>()
    val favoriteRecipes: LiveData<Map<String, Recipe>> = _favoriteRecipes

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean> get() = _favoriteState


    fun loadFavorites() {
        viewModelScope.launch {
            val recipes = repository.getFavoriteRecipes()
            _favoriteRecipes.value = recipes
        }
    }


    fun setInitialFavoriteState(isFavorite: Boolean) {
        _favoriteState.value = isFavorite
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            if (recipe.isFavorite) {
                repository.removeFavorite(recipe.id)
                recipe.isFavorite = false
            } else {
                repository.addFavorite(recipe)
                recipe.isFavorite = true
            }

            _favoriteState.value = recipe.isFavorite
        }
    }

}
