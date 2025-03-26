package com.example.recipecatalog.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecatalog.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AuthUiState(val isSuccess: Boolean = false, val errorMessage: String? = null)


@HiltViewModel
class UserAuthViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> get() = _deleteResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            _uiState.value = result.fold(
                onSuccess = { AuthUiState(isSuccess = true) },
                onFailure = { AuthUiState(isSuccess = false, errorMessage = it.message) }
            )
        }
    }


    fun register(email: String, password: String) {
        _uiState.value = AuthUiState(isSuccess = false)

        viewModelScope.launch {
            val result = repository.register(email, password)
            _uiState.value = if (result.isSuccess) {
                AuthUiState(isSuccess = true)
            } else {
                AuthUiState(isSuccess = false, errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun logout(){
        repository.logout();
    }

    fun delete() {
        viewModelScope.launch {
            val isDeleted = repository.delete()
            _deleteResult.postValue(isDeleted)
        }
    }

}


