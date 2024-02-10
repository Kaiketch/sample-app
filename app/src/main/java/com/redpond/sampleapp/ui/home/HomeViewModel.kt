package com.redpond.sampleapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

//    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
//    val uiState: StateFlow<UiState> = _uiState

    val uiState: StateFlow<UiState> =
        userRepository.getUsers(10, 0, "UTzPH0ckXUPqj4QTCiZG5SLZO52K1KNI", 2, 1).map {
            UiState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UiState.Loading
        )

    fun onStart() {
//        viewModelScope.launch {
//            userRepository.getUsers(10, 0, "UTzPH0ckXUPqj4QTCiZG5SLZO52K1KNI", 2, 1)
//                .catch {
//                    _uiState.update { UiState.Error("An unexpected error occurred") }
//                }.collect { users ->
//                    _uiState.update { UiState.Success(users) }
//                }
//        }
    }
}
