package com.study.marketlist.service

import com.study.marketlist.network.data.ErrorData
import com.study.marketlist.network.data.LoginData
import com.study.marketlist.network.data.UserData
import com.study.marketlist.service.ClientService.Companion.ERROR_ID
import com.study.marketlist.service.ClientService.Companion.ERROR_MESSAGE
import com.study.marketlist.service.ClientService.Companion.USER_EMAIL
import com.study.marketlist.service.ClientService.Companion.USER_ID
import com.study.marketlist.service.ClientService.Companion.USER_NAME
import com.study.marketlist.service.ClientService.Companion.USER_PASSWORD
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ClientServiceTest {
    private lateinit var service: ClientService

    @Before
    fun setUp() {
        service = ClientService()
    }

    @Test
    fun `WHEN received a email and password WITH correct information SHOULD return a user`() {
        val result = service.login(USER_EMAIL, USER_PASSWORD)
        val expected = loginDataSuccess

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN received a email and password WITH incorrect information SHOULD return a error`() {
        val result = service.login(USER_EMAIL, USER_PASSWORD_INVALID)
        val expected = loginDataError

        assertEquals(expected, result)
    }

    companion object {
        private const val USER_PASSWORD_INVALID = "12345679"
        private val loginDataSuccess = LoginData(
            user = UserData(
                id = USER_ID,
                name = USER_NAME,
                email = USER_EMAIL
            ),
            error = null
        )
        private val loginDataError = LoginData(
            user = null,
            error = ErrorData(
                id = ERROR_ID,
                message = ERROR_MESSAGE
            )
        )
    }
}