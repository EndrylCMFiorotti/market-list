package com.study.marketlist.network.response

data class LoginResponse(
    val user: UserResponse?,
    val error: ErrorResponse?
)