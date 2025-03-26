package com.example.recipecatalog.data.db

import android.util.Log
import com.example.recipecatalog.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getRecipes(): List<Recipe> {
        return try {
            val snapshot = firestore.collection("recipes").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Recipe::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error getting recipes", e)
            emptyList()
        }
    }

}