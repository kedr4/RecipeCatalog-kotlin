package com.example.recipecatalog

import android.app.Application
import com.example.recipecatalog.data.UserRepository
import com.example.recipecatalog.data.db.AuthDataSource
import com.example.recipecatalog.data.db.FavoritesDataSource
import com.example.recipecatalog.data.db.UserDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class MyApp : Application()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): AuthDataSource {
        return AuthDataSource(firebaseAuth, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideFavoritesDataSource(
        firebaseFirestore: FirebaseFirestore
    ): FavoritesDataSource {
        return FavoritesDataSource(firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        authDataSource: AuthDataSource,
        userDataSource: UserDataSource
    ): UserRepository {
        return UserRepository(authDataSource, userDataSource)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): UserDataSource {
        return UserDataSource(firebaseAuth, firebaseFirestore)
    }
}
