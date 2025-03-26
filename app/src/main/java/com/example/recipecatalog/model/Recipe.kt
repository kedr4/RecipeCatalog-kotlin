package com.example.recipecatalog.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: String = "",
    val title: String = "",
    val ingredients: String = "",
    val instructions: String = "",
    val imageUrl: String = "",
    val images: List<String> = emptyList(),
    val category: String = "",
    val timeEstimated: Int = 0,

    // Изменяемые свойства с аннотациями для Firestore
    @get:PropertyName("isGlutenFree") @set:PropertyName("isGlutenFree")
    var isGlutenFree: Boolean = false,

    @get:PropertyName("isDairyFree") @set:PropertyName("isDairyFree")
    var isDairyFree: Boolean = false,

    @get:PropertyName("isVegan") @set:PropertyName("isVegan")
    var isVegan: Boolean = false,


    var isFavorite: Boolean = false

) : Parcelable