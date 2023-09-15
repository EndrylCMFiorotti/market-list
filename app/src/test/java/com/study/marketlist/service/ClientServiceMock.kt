package com.study.marketlist.service

import com.study.marketlist.network.data.ErrorData
import com.study.marketlist.network.data.LoginData
import com.study.marketlist.network.data.UserData

class ClientServiceMock : ClientService() {
    override fun login(email: String, password: String): LoginData {
        return if (email == USER_EMAIL && password == USER_PASSWORD) {
            LoginData(
                user = UserData(
                    id = USER_ID,
                    name = USER_NAME,
                    email = USER_EMAIL
                ),
                error = null
            )
        } else {
            LoginData(
                user = null,
                error = ErrorData(
                    id = ERROR_ID,
                    message = ERROR_MESSAGE
                )
            )
        }
    }

    companion object {
        private const val USER_ID = 1
        private const val USER_NAME = "Gaspar"
        private const val USER_EMAIL = "gabriel@gmail.com"
        private const val USER_PASSWORD = "123456789"

        private const val ERROR_ID = 1
        private const val ERROR_MESSAGE = "error"
    }
}