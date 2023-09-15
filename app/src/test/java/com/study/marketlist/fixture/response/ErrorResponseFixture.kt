package com.study.marketlist.fixture.response

import com.study.marketlist.network.response.ErrorResponse

class ErrorResponseFixture(
    val id: Int,
    val message: String
) {
    companion object {
        fun getErrorResponseComplete(
            withId: Int = 1,
            withMessage: String = "error"
        ) = ErrorResponseFixture(
            id = withId,
            message = withMessage
        )
    }

    fun build() = ErrorResponse(
        id = id,
        message = message
    )
}