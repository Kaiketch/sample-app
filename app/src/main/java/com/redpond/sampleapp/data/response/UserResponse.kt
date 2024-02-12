package com.redpond.sampleapp.data.response

import com.redpond.sampleapp.domain.model.User
import com.redpond.sampleapp.util.parseToYyyyMMddHHmm
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class UserDataResponse(
    val memberData: List<UserResponse>,
)

data class UserResponse(
    val memberId: Int,
    val memberName: String,
    val iconPath: String,
    val insertTime: String,
)

fun UserResponse.toUser() = User(
    id = memberId,
    name = memberName,
    imageUrl = iconPath,
    created = insertTime.parseToYyyyMMddHHmm()
)
