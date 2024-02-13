package com.redpond.sampleapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redpond.sampleapp.domain.model.User
import com.redpond.sampleapp.domain.repository.UserRepositoryInterface
import com.redpond.sampleapp.util.catchAppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepositoryInterface
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val users: List<User>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

//    val uiState: StateFlow<UiState> =
//        userRepository.getUsers(10, 0, "UTzPH0ckXUPqj4QTCiZG5SLZO52K1KNI", 2, 1).map {
//            UiState.Success(it)
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            initialValue = UiState.Loading
//        )

    init {
        viewModelScope.launch {
            userRepository.getUsers(10, 0, "UTzPH0ckXUPqj4QTCiZG5SLZO52K1KNI", 2, 1)
                .catchAppError {
                    Log.e("HomeViewModel", "An unexpected error occurred", it)
                    _uiState.update { UiState.Error("An unexpected error occurred") }
                }.collect { users ->
                    _uiState.update { UiState.Success(users) }
                }
        }
    }

    fun onStart() {

    }

    fun onLocalSortClick(localSortType: LocalSortType) {
        (_uiState.value as? UiState.Success)?.let {
            val users = it.users.sortedWith { o1, o2 ->
                when (localSortType) {
                    LocalSortType.ID_ASC -> {
                        return@sortedWith if (o1.id - o2.id == 0) 0 else if (o1.id - o2.id > 0) 1 else -1
                    }

                    LocalSortType.ID_DESC -> {
                        return@sortedWith if (o2.id - o1.id == 0) 0 else if (o2.id - o1.id > 0) 1 else -1
                    }
                }
            }
            _uiState.update { UiState.Success(users) }
        }
    }

    enum class LocalSortType {
        ID_ASC, ID_DESC
    }
}
