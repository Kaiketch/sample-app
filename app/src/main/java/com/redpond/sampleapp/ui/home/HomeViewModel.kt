package com.redpond.sampleapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redpond.sampleapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun onStart() {
        viewModelScope.launch {
            runCatching {
                userRepository.getUsers(10, 0, "UTzPH0ckXUPqj4QTCiZG5SLZO52K1KNI", 2, 1)
            }.onSuccess {
                val users = it.memberData
                val user = users[0]
                val id = user.memberId
            }.onFailure {
                // TODO

            }
        }
    }
}