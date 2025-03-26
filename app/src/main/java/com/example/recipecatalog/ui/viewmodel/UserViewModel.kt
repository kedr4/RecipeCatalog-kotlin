package com.example.recipecatalog.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecatalog.data.UserRepository
import com.example.recipecatalog.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableLiveData<UserState>()
    val userState: LiveData<UserState> = _userState

    private val _isProfileComplete = MutableLiveData<Boolean>()
    val isProfileComplete: LiveData<Boolean> = _isProfileComplete

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val userProfile = userRepository.getUserProfile()
            if (userProfile != null) {
                _userState.value = UserState(userProfile)
                _isProfileComplete.value = true
            } else {
                _isProfileComplete.value = false
            }
        }
    }

    fun saveUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            userRepository.saveUserProfile(userProfile)
            _userState.value = UserState(userProfile)
            _isProfileComplete.value = true
        }
    }
}

data class UserState(
    val userProfile: UserProfile? = null
)
