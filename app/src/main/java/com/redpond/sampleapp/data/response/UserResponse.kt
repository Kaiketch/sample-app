package com.redpond.sampleapp.data.response

data class UserDataResponse(
    val memberData: List<UserResponse>,
)

data class UserResponse(
    val memberId: Int,
    val memberName: String,
    val iconPath: String,
)
