package com.redpond.sampleapp.domain.model

import org.junit.Test

class UserTest {

    @Test
    fun validate() {
        val user = User.fakeUser.copy(name = "valid name")
        user.validate()
    }

    @Test
    fun validate_throw_exception_if_invalid_name() {
        val user = User.fakeUser.copy(name = "na")
        try {
            user.validate()
        } catch (e: IllegalStateException) {
            assert(e.message == "Name is too short")
        }
    }
}