package com.study.marketlist.network.response

import com.study.marketlist.presentation.UserPresentation

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String
) {
    fun toUserPresentation() = UserPresentation(
        id = id,
        name = name
    )
}