package com.example.recipecatalog.data

import com.example.recipecatalog.data.db.AuthDataSource
import com.example.recipecatalog.data.db.UserDataSource
import com.example.recipecatalog.model.UserProfile

data class UserRepository(
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource
) {
    suspend fun login(email: String, password: String) = authDataSource.signIn(email, password)
    suspend fun register(email: String, password: String) = authDataSource.signUp(email, password)
    fun isUserLoggedIn(): Boolean {
        return authDataSource.isUserLoggedIn();
    }

    fun logout() {
        authDataSource.logout()
    }

    suspend fun delete(): Boolean {
        return authDataSource.delete()
    }

    suspend fun getUserProfile(): UserProfile? {
        return userDataSource.getUserProfile()
    }

    suspend fun saveUserProfile(userProfile: UserProfile) {
        userDataSource.saveUserProfile(userProfile)
    }


}

