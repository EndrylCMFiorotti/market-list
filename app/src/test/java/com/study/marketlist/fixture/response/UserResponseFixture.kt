package com.study.marketlist.fixture.response

import com.study.marketlist.network.data.UserData
import com.study.marketlist.network.response.UserResponse

class UserResponseFixture(
    val id: Int,
    val name: String,
    val email: String
) {
    companion object {
        fun getUserResponseComplete(
            withId: Int = 1,
            withName: String = "Endryl Fiorotti",
            withEmail: String = "endryl@gmail.com"
        ) = UserResponseFixture(
            id = withId,
            name = withName,
            email = withEmail
        )
    }

    fun build() = UserResponse(
        id = id,
        name = name,
        email = email
    )
}