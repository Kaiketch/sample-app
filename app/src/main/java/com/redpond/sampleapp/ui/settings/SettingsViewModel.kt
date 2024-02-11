package com.redpond.sampleapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
                .catch {
                    _uiState.value = UiState.Error("An unexpected error occurred")
                }.collect { user ->
                    _uiState.value = UiState.Success(user)
                }
        }
    }

    fun onEditClick(
        name: String,
    ) {
        (_uiState.value as? UiState.Success)?.let {
            viewModelScope.launch {
                runCatching {
                    userRepository.editUser(
                        "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj",
                        name,
                        "",
                        1,
                        1,
                        1,
                        1,
                        1,
                    )
                }.onSuccess {

                }.onFailure {

                }
            }
        }
    }
}