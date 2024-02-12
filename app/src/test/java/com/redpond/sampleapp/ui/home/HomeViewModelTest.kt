package com.redpond.sampleapp.ui.home

import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_success() = runTest {
        // Given
        val userRepository = mockk<UserRepository> {
            coEvery { getUsers(any(), any(), any(), any(), any()) } returns flow {
                emit(
                    listOf(
                        User.fakeUser
                    )
                )
            }
        }
        val viewModel = HomeViewModel(userRepository)
        val result = mutableListOf<HomeViewModel.UiState>()

        // When
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                result.add(it)
            }
        }
        job.cancel()

        // Then
        assertEquals(
            HomeViewModel.UiState.Loading,
            result[0]
        )
        assertEquals(
            HomeViewModel.UiState.Success(listOf(User.fakeUser)),
            result[1]
        )
    }

    @Test
    fun uiState_error() = runTest {
        // Given
        val userRepository = mockk<UserRepository> {
            coEvery { getUsers(any(), any(), any(), any(), any()) } returns flow {
                throw Exception()
            }
        }
        val viewModel = HomeViewModel(userRepository)
        val result = mutableListOf<HomeViewModel.UiState>()

        // When
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                result.add(it)
            }
        }
        job.cancel()

        // Then
        assertEquals(
            HomeViewModel.UiState.Loading,
            result[0]
        )
        assertEquals(
            HomeViewModel.UiState.Error("An unexpected error occurred"),
            result[1]
        )
    }
}