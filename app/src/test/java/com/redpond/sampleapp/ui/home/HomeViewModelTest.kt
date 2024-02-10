package com.redpond.sampleapp.ui.home

import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
                        User(
                            1,
                            "name",
                            "iconPath"
                        )
                    )
                )
            }
        }
        val viewModel = HomeViewModel(userRepository)
        val expected = HomeViewModel.UiState.Success(listOf(User(1, "name", "iconPath")))

        // When
        viewModel.onStart()

        // Then
        assertEquals(expected, viewModel.uiState.value)
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
        val expected = HomeViewModel.UiState.Error("An unexpected error occurred")

        // When
        viewModel.onStart()

        // Then
        assertEquals(expected, viewModel.uiState.value)
    }
}