package com.study.marketlist.fixture.data

import com.study.marketlist.network.data.ErrorData
import com.study.marketlist.network.data.LoginData
import com.study.marketlist.network.data.UserData

class LoginDataFixture(
    val user: UserData?,
    val error: ErrorData?
) {
    companion object {
        fun getLoginDataComplete(
            withUser: UserData? = UserData(
                id = 1,
                name = "Endryl Fiorotti",
                email = "endryl@gmail.com"
            ),
            withError: ErrorData? = ErrorData(
                id = 1,
                message = "error"
            )
        ) = LoginDataFixture(
            user = withUser,
            error = withError
        )
    }

    fun build() = LoginData(
        user = user,
        error = error
    )
}