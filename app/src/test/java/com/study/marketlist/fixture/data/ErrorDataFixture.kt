package com.study.marketlist.fixture.data

import com.study.marketlist.network.data.ErrorData

class ErrorDataFixture(
    val id: Int,
    val message: String
) {
    companion object {
        fun getErrorDataComplete(
            withId: Int = 1,
            withMessage: String = "error"
        ) = ErrorDataFixture(
            id = withId,
            message = withMessage
        )
    }

    fun build() = ErrorData(
        id = id,
        message = message
    )
}