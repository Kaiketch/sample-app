package com.redpond.sampleapp.data.repository

import com.redpond.sampleapp.data.api.UserApi
import com.redpond.sampleapp.data.response.toUser
import com.redpond.sampleapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        accessToken: String,
        memberName: String,
        comment: String,
        areaCode: Int,
        experienceCode: Int,
        styleCode1: Int,
        styleCode2: Int,
        styleCode3: Int,
    ) {
        userApi.editUser(
            accessToken,
            memberName,
            comment,
            areaCode,
            experienceCode,
            styleCode1,
            styleCode2,
            styleCode3,
        )
    }
}
