package com.redpond.sampleapp.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import com.redpond.sampleapp.util.catchAppError
import com.redpond.sampleapp.util.onAppFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {
    sealed class UiState {
        data object Loading : UiState()
        data class Success(val user: User) : UiState()

        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            userRepository.getUserById(75362, 10, 0, "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj", 2)
                .catchAppError {
                    Log.e("SettingsViewModel", "An unexpected error occurred", it)
                    _uiState.value = UiState.Error("An unexpected error occurred")
                }.collect { user ->
                    _uiState.value = UiState.Success(user)
                }
        }
    }

    fun onNameChange(name: String) {
        (_uiState.value as? UiState.Success)?.let { success ->
            _uiState.update { UiState.Success(success.user.copy(name = name)) }
        }
    }

    fun onEditClick() {
        (_uiState.value as? UiState.Success)?.let {
            viewModelScope.launch {
                runCatching {
                    userRepository.editUser(it.user)
                }.onSuccess {

                }.onAppFailure { error ->
                    _uiState.update { UiState.Error(error.message) }
                }
            }
        }
    }
}