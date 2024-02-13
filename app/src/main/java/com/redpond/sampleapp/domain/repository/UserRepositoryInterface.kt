package com.redpond.sampleapp.domain.repository

import com.redpond.sampleapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepositoryInterface {

    fun getUsers(
        limit: Int,
        offset: Int,
        accessToken: String,
        conditionCode: Int,
        sortType: Int
    ): Flow<List<User>>

    fun getUserById(
        id: Int,
        limit: Int,
        offset: Int,
        accessToken: String,
        conditionCode: Int,
    ): Flow<User>

    suspend fun editUser(
        user: User,
    )

    suspend fun editUserImage(
        file: File,
    )
}