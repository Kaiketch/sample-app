package com.redpond.sampleapp.data.repository

import com.redpond.sampleapp.data.api.UserApi
import com.redpond.sampleapp.data.response.toUser
import com.redpond.sampleapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    fun getUsers(
        limit: Int,
        offset: Int,
        accessToken: String,
        conditionCode: Int,
        sortType: Int
    ): Flow<List<User>> {
        return flow {
            emit(userApi.fetchUsers(
                limit,
                offset,
                accessToken,
                conditionCode,
                sortType
            ).memberData.map { it.toUser() })
        }
    }

    fun getUserById(
        id: Int,
        limit: Int,
        offset: Int,
        accessToken: String,
        conditionCode: Int,
    ): Flow<User> {
        return flow {
            emit(
                userApi.fetchUserById(
                    id,
                    limit,
                    offset,
                    accessToken,
                    conditionCode,
                ).memberData.first().toUser()
            )
        }
    }

    suspend fun editUser(
        user: User,
    ) {
        userApi.editUser(
            "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj",
            if (user.validateName()) user.name else throw IllegalArgumentException("Name is too short"),
            "",
            1,
            1,
            1,
            1,
            1,
        )
    }

    suspend fun editUserImage(
        file: File,
    ) {
        val reqBody1 =
            "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj".toRequestBody("text/*".toMediaTypeOrNull())
        val part1 = MultipartBody.Part.createFormData("access_token", null, reqBody1)
        val reqBody2 = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part2 = MultipartBody.Part.createFormData("image", file.name, reqBody2)
        userApi.editUserImage(
            part1,
            part2,
        )
    }
}
