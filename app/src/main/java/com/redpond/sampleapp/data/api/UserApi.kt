package com.redpond.sampleapp.data.api

import com.redpond.sampleapp.data.response.UserDataResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

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

    @FormUrlEncoded
    @POST("/api/v2/members/edit")
    suspend fun editUser(
        @Field("access_token") accessToken: String,
        @Field("member_name") memberName: String,
        @Field("comment") comment: String,
        @Field("area_code") areaCode: Int,
        @Field("experience_code") experienceCode: Int,
        @Field("style_code_1") styleCode1: Int,
        @Field("style_code_2") styleCode2: Int,
        @Field("style_code_3") styleCode3: Int,
    )

    @Multipart
    suspend fun editUserImage(
        @Field("access_token") accessToken: String,
        @Part("image") image: File,
    )
}
