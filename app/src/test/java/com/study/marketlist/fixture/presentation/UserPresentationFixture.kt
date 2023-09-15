package com.study.marketlist.fixture.presentation

import com.study.marketlist.presentation.UserPresentation

class UserPresentationFixture(
    val id: Int,
    val name: String
) {
    companion object {
        fun getUserPresentationComplete(
            withId: Int = 1,
            withName: String = "Gaspar"
        ) = UserPresentationFixture(
            id = withId,
            name = withName
        )
    }

    fun build() = UserPresentation(
        id = id,
        name = name
    )
}