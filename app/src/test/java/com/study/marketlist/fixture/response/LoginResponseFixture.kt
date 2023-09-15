package com.study.marketlist.fixture.response

import com.study.marketlist.network.response.ErrorResponse
import com.study.marketlist.network.response.LoginResponse
import com.study.marketlist.network.response.UserResponse

class LoginResponseFixture(
    val id: Int,
    val name: String,
    val email: String,
    val errorId: Int,
    val errorMessage: String,
    val hasError: Boolean
) {
    companion object {
        fun getLoginResponseComplete(
            withId: Int = 1,
            withName: String = "Gaspar",
            withEmail: String = "gabriel@gmail.com",
            withErrorId: Int = 1,
            withErrorMessage: String = "error",
            withHasError: Boolean = false
        ) = LoginResponseFixture(
            id = withId,
            name = withName,
            email = withEmail,
            errorId = withErrorId,
            errorMessage = withErrorMessage,
            hasError = withHasError
        )
    }

    fun build() = LoginResponse(
        user = if (hasError.not()) UserResponse(
            id = id,
            name = name,
            email = email
        ) else null,
        error = if (hasError) ErrorResponse(
            id = errorId,
            message = errorMessage
        ) else null
    )
}