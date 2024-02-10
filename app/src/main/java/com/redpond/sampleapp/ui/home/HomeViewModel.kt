package com.redpond.sampleapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val users: List<User>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun onStart() {
        viewModelScope.launch {
            runCatching {
                userRepository.getUsers(10, 0, "UTzPH0ckXUPqj4QTCiZG5SLZO52K1KNI", 2, 1)
            }.onSuccess { users ->
                _uiState.update { UiState.Success(users) }
            }.onFailure {
                _uiState.update { UiState.Error("Failed to fetch users") }
            }
        }
    }
}