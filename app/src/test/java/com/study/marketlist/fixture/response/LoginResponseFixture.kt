package com.study.marketlist.fixture.response

import com.study.marketlist.network.response.ErrorResponse
import com.study.marketlist.network.response.LoginResponse
import com.study.marketlist.network.response.UserResponse

class LoginResponseFixture(
    val user: UserResponse?,
    val error: ErrorResponse?
) {
    companion object {
        fun getLoginResponseComplete(
            withUser: UserResponse? = UserResponse(
                id = 1,
                name = "Endryl Fiorotti",
                email = "endryl@gmail.com"
            ),
            withError: ErrorResponse? = ErrorResponse(
                id = 1,
                message = "error"
            )
        ) = LoginResponseFixture(
            user = withUser,
            error = withError
        )
    }

    fun build() = LoginResponse(
        user = user,
        error = error
    )
}