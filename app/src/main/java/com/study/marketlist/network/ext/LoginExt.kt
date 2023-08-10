package com.study.marketlist.network.ext

import com.study.marketlist.network.data.ErrorData
import com.study.marketlist.network.data.LoginData
import com.study.marketlist.network.data.UserData
import com.study.marketlist.network.response.ErrorResponse
import com.study.marketlist.network.response.LoginResponse
import com.study.marketlist.network.response.UserResponse

fun LoginData.toResponse() = LoginResponse(
    user = user?.toResponse(),
    error = error?.toResponse()
)

fun UserData.toResponse() = UserResponse(
    id = id,
    name = name,
    email = email
)

fun ErrorData.toResponse() = ErrorResponse(
    id = id,
    message = message
)