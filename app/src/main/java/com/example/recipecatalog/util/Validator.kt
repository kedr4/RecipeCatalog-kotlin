package com.example.recipecatalog.util

import android.util.Patterns
import com.example.recipecatalog.model.UserProfile

class Validator {
    companion object {
        // Константы для валидации
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_NAME_LENGTH = 50
        private const val MIN_DESCRIPTION_LENGTH = 10
        private const val MIN_PHONE_NUMBER_LENGTH = 8 // Для формата +375XXXXXXXXX

        fun validateCredentials(email: String, password: String): String? {
            return when {
                email.isBlank() -> "Email is required"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Please enter a valid email address"
                password.isBlank() -> "Password is required"
                password.length < MIN_PASSWORD_LENGTH -> "Password must be at least $MIN_PASSWORD_LENGTH characters long"
                else -> null
            }
        }

        fun validateProfile(userProfile: UserProfile): String? {
            return when {
                userProfile.fullName.isNullOrBlank() -> "Full name is required"
                userProfile.fullName.length > MAX_NAME_LENGTH -> "Name should not exceed $MAX_NAME_LENGTH characters"
                !userProfile.fullName.matches(Regex("^[\\p{L} .'-]+\$")) -> "Name contains invalid characters"

                userProfile.birthDate.isNullOrBlank() -> "Birth date is required"

                userProfile.description.isNullOrBlank() -> "Description is required"
                userProfile.description.length < MIN_DESCRIPTION_LENGTH -> "Description should be at least $MIN_DESCRIPTION_LENGTH characters"

                userProfile.phoneNumber.isNullOrBlank() -> "Phone number is required"
                !isValidBelarusPhone(userProfile.phoneNumber) -> "Invalid phone number format (use +375XXXXXXXXX)"

                userProfile.address.isNullOrBlank() -> "Address is required"
                userProfile.address.length < 5 -> "Address is too short"

                userProfile.gender.isNullOrBlank() -> "Gender is required"
                !listOf("male", "female", "other").contains(userProfile.gender.lowercase()) -> "Invalid gender specified"

                userProfile.occupation.isNullOrBlank() -> "Occupation is required"
                userProfile.interests.isNullOrEmpty() -> "At least one interest is required"
                else -> null
            }
        }

        private fun isValidBelarusPhone(phone: String): Boolean {
            return phone.length > MIN_PHONE_NUMBER_LENGTH &&
                    phone.startsWith("+375") || phone.startsWith("375")
                    phone.substring(4).all { it.isDigit() }
        }
    }
}