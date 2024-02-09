package com.redpond.sampleapp.data.repository

import com.redpond.sampleapp.data.api.UserApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getUsers(
        limit: Int,
        offset: Int,
        accessToken: String,
        conditionCode: Int,
        sortType: Int
    ) = userApi.fetchUsers(limit, offset, accessToken, conditionCode, sortType)
}
