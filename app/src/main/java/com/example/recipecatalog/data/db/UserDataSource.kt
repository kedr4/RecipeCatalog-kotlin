package com.example.recipecatalog.data.db

import com.example.recipecatalog.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun getUserProfile(): UserProfile? {
        val user = auth.currentUser
        return user?.let {
            val doc = firestore.collection("users").document(user.uid).get().await()
            doc.toObject(UserProfile::class.java)
        }
    }

    suspend fun saveUserProfile(userProfile: UserProfile) {
        val user = auth.currentUser
        user?.let {
            val userProfileWithEmail = userProfile.copy(email = user.email)

            firestore.collection("users").document(user.uid).set(userProfileWithEmail).await()
        }
    }

}

