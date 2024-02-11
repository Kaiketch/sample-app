package com.redpond.sampleapp.data.api

import com.redpond.sampleapp.data.response.UserDataResponse
import com.redpond.sampleapp.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/api/v2/members/search")
    suspend fun fetchUsers(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("access_token") accessToken: String,
        @Query("condition_code") conditionCode: Int,
        @Query("sort_type") sortType: Int,
    ): UserDataResponse

    @GET("/api/v2/members/details")
    suspend fun fetchUserById(
        @Query("member_id") memberId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("access_token") accessToken: String,
        @Query("condition_code") conditionCode: Int,
    ): UserDataResponse
}
