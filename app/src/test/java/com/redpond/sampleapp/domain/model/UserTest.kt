package com.redpond.sampleapp.domain.model

import org.junit.Test

class UserTest {

    @Test
    fun validateName() {
        val user = User.fakeUser.copy(name = "name")
        assert(user.validateName())
    }

    @Test
    fun validateName_short() {
        val user = User.fakeUser.copy(name = "na")
        assert(!user.validateName())
    }
}