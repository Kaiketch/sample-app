package com.redpond.sampleapp.ui.home

import com.google.common.truth.Truth
import com.redpond.sampleapp.data.repository.UserRepository
import com.redpond.sampleapp.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
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
    fun init_uiState_set_success_user() = runTest {
        // Given
        val userRepository = mockk<UserRepository> {
            coEvery { getUsers(any(), any(), any(), any(), any()) } returns flowOf(
                listOf(
                    User.fakeUser
                )
            )

        }
        val viewModel = HomeViewModel(userRepository)

        Truth.assertThat(viewModel.uiState.value)
            .isEqualTo(HomeViewModel.UiState.Success(listOf(User.fakeUser)))
    }

    @Test
    fun init_uiState_set_error() = runTest {
        // Given
        val userRepository = mockk<UserRepository> {
            coEvery { getUsers(any(), any(), any(), any(), any()) } returns flow {
                throw Exception()
            }
        }

        val viewModel = HomeViewModel(userRepository)

        Truth.assertThat(viewModel.uiState.value)
            .isEqualTo(HomeViewModel.UiState.Error("An unexpected error occurred"))
    }
}