package com.redpond.sampleapp.domain.model

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String,
) {
    fun validateName(): Boolean {
        return name.length > 2
    }

    companion object {
        val fakeUser = User(0, "default", "default")
    }
}
