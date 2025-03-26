package com.example.recipecatalog.data.db

import com.example.recipecatalog.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getFavorites(userId: String): List<Recipe> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()


            snapshot.documents.mapNotNull { it.toObject(Recipe::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addFavorite(userId: String, car: Recipe) {
        try {
            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(car.id)
                .set(car)
                .await()
        } catch (e: Exception) {

        }
    }

    suspend fun removeFavorite(userId: String, carId: String) {
        try {
            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(carId)
                .delete()
                .await()
        } catch (e: Exception) {

        }
    }
}
