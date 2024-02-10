package com.redpond.sampleapp.data.response

import com.redpond.sampleapp.domain.model.User

data class UserDataResponse(
    val memberData: List<UserResponse>,
)

data class UserResponse(
    val memberId: Int,
    val memberName: String,
    val iconPath: String,
)

fun UserResponse.toUser() = User(
    id = memberId,
    name = memberName,
    imageUrl = iconPath,
)
