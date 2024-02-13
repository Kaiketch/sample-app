package com.redpond.sampleapp.data.repository

import com.redpond.sampleapp.data.api.UserApi
import com.redpond.sampleapp.data.datastore.SettingsDataStore
import com.redpond.sampleapp.data.response.UserDataResponse
import com.redpond.sampleapp.data.response.UserResponse
import com.redpond.sampleapp.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    @Test
    fun getUserById_should_request_if_cache_is_null() = runTest {
        val userApi = mockk<UserApi> {
            coEvery { fetchUsers(any(), any(), any(), any(), any()) } returns UserDataResponse(
                memberData = listOf(UserResponse.fakeUserResponse)
            )
        }
        val settingsDataStore = mockk<SettingsDataStore>() {
            coEvery { shouldUserCache } returns flowOf(true)
        }
        val userRepository = UserRepository(userApi, settingsDataStore)

        userRepository.getUsers(10, 0, "accessToken", 0, 0).collect()

        coVerify(exactly = 1) { userApi.fetchUsers(10, 0, "accessToken", 0, 0) }
    }

    @Test
    fun getUserById_should_request_if_cache_false_and_cache_is_not_null() = runTest {
        val userApi = mockk<UserApi> {
            coEvery { fetchUsers(any(), any(), any(), any(), any()) } returns UserDataResponse(
                memberData = listOf(UserResponse.fakeUserResponse)
            )
        }
        val settingsDataStore = mockk<SettingsDataStore>() {
            coEvery { shouldUserCache } returns flowOf(false)
        }
        val userRepository = UserRepository(userApi, settingsDataStore)
        userRepository.dispatcher = StandardTestDispatcher(testScheduler)

        userRepository.getUsers(10, 0, "accessToken", 0, 0).collect()
        userRepository.getUsers(10, 0, "accessToken", 0, 0).collect()

        coVerify(exactly = 2) { userApi.fetchUsers(10, 0, "accessToken", 0, 0) }
    }

    @Test
    fun getUserById_should_request_1_time_if_cache_true_and_cache_is_not_null() = runTest {
        val userApi = mockk<UserApi> {
            coEvery { fetchUsers(any(), any(), any(), any(), any()) } returns UserDataResponse(
                memberData = listOf(UserResponse.fakeUserResponse)
            )
        }
        val settingsDataStore = mockk<SettingsDataStore>() {
            coEvery { shouldUserCache } returns flowOf(true)
        }
        val userRepository = UserRepository(userApi, settingsDataStore)
        userRepository.dispatcher = StandardTestDispatcher(testScheduler)

        userRepository.getUsers(10, 0, "accessToken", 0, 0).collect()
        userRepository.getUsers(10, 0, "accessToken", 0, 0).collect()

        coVerify(exactly = 1) { userApi.fetchUsers(10, 0, "accessToken", 0, 0) }
    }

    @Test
    fun editUser_should_call_api_if_valid_user() = runTest {
        val userApi = mockk<UserApi> {
            coEvery {
                editUser(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Unit
        }
        val settingsDataStore = mockk<SettingsDataStore>(relaxed = true)
        val userRepository = UserRepository(userApi, settingsDataStore)
        userRepository.dispatcher = StandardTestDispatcher(testScheduler)

        userRepository.editUser(User.fakeUser.copy(name = "validName"))

        coVerify(exactly = 1) {
            userApi.editUser(
                any(),
                "validName",
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
    }
}