package com.study.marketlist.fixture.data

import com.study.marketlist.network.data.UserData

class UserDataFixture(
    val id: Int,
    val name: String,
    val email: String
) {
    companion object {
        fun getUserDataComplete(
            withId: Int = 1,
            withName: String = "Endryl Fiorotti",
            withEmail: String = "endryl@gmail.com"
        ) = UserDataFixture(
            id = withId,
            name = withName,
            email = withEmail
        )
    }

    fun build() = UserData(
        id = id,
        name = name,
        email = email
    )
}