package com.redpond.sampleapp.ui.settings

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.redpond.sampleapp.domain.model.User
import com.redpond.sampleapp.domain.repository.UserRepositoryInterface
import com.redpond.sampleapp.util.MediaManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onActivityResult_shouldCallUpdateImage() {
        val userRepository = mockk<UserRepositoryInterface> {
            coEvery { getUserById(any(), any(), any(), any(), any()) } returns flowOf(User.fakeUser)
            coEvery { editUserImage(any()) } returns Unit
        }
        val mediaManager = mockk<MediaManager>(relaxed = true)
        val viewModel = SettingsViewModel(userRepository, mediaManager)
        val result = mockk<ActivityResult> {
            every { resultCode } returns Activity.RESULT_OK
            every { data?.data } returns mockk<Uri>()
        }

        viewModel.onActivityResult(result)

        coVerify { userRepository.editUserImage(any()) }
    }
}