package com.redpond.sampleapp.data.repository

import com.redpond.sampleapp.data.api.UserApi
import com.redpond.sampleapp.data.response.toUser
import com.redpond.sampleapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        userApi.editUserImage(
            "mvQgHGMTJvMbFZkO3KnXOV2okgzYsPQj",
            file,
        )
    }
}
