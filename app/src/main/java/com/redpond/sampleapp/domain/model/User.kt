package com.redpond.sampleapp.domain.model

import java.time.LocalDateTime

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val created: LocalDateTime?,
) {
    fun validateName(): Boolean {
        return name.length > 2
    }

    companion object {
        val fakeUser = User(0, "default", "default", LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0))
    }
}
