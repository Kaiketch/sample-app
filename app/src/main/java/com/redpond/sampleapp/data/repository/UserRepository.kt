package com.redpond.sampleapp.data.repository

import com.redpond.sampleapp.data.api.UserApi
import com.redpond.sampleapp.data.datastore.SettingsDataStore
import com.redpond.sampleapp.data.response.UserDataResponse
import com.redpond.sampleapp.data.response.toUser
import com.redpond.sampleapp.domain.model.User
import com.redpond.sampleapp.domain.repository.UserRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val settingsDataStore: SettingsDataStore,
) : UserRepositoryInterface {
    var dispatcher: CoroutineDispatcher = Dispatchers.IO

    private var userDataResponse: UserDataResponse? = null

    override fun getUsers(
        limit: Int,
        offset: Int,
        accessToken: String,
        conditionCode: Int,
        sortType: Int
    ): Flow<List<User>> {
        return flow {
            val shouldUserCache = settingsDataStore.shouldUserCache.first()
            val res = if (shouldUserCache && userDataResponse != null) {
                userDataResponse
            } else {
                userApi.fetchUsers(
                    limit,
                    offset,
                    accessToken,
                    conditionCode,
                    sortType
                ).also { userDataResponse = it }
            }
            emit(res!!.memberData.map { it.toUser() })
        }.flowOn(dispatcher)
    }

    override fun getUserById(
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
        }.flowOn(dispatcher)
    }

    override suspend fun editUser(
        user: User,
    ) {
        user.validate()
        withContext(dispatcher) {
            userApi.editUser(
                "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj",
                user.name,
                "",
                1,
                1,
                1,
                1,
                1,
            )
        }
    }

    override suspend fun editUserImage(
        file: File,
    ) {
        val reqBody1 =
            "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj".toRequestBody("text/*".toMediaTypeOrNull())
        val part1 = MultipartBody.Part.createFormData("access_token", null, reqBody1)
        val reqBody2 = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part2 = MultipartBody.Part.createFormData("image", file.name, reqBody2)
        withContext(dispatcher) {
            userApi.editUserImage(
                part1,
                part2,
            )
        }
    }
}
