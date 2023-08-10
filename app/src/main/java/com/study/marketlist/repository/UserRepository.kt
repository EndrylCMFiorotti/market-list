package com.study.marketlist.repository

import com.study.marketlist.network.data.ErrorData
import com.study.marketlist.network.data.LoginData
import com.study.marketlist.network.data.UserData
import com.study.marketlist.network.ext.toResponse
import com.study.marketlist.network.handler.requestHandler
import com.study.marketlist.network.response.LoginResponse
import com.study.marketlist.network.wrapper.ResultWrapper

class UserRepository {
    suspend fun checkLogin(email: String, password: String): ResultWrapper<LoginResponse> =
        requestHandler {
            if (email == USER_EMAIL && password == USER_PASSWORD) {
                LoginData(
                    user = UserData(
                        id = USER_ID,
                        name = USER_NAME,
                        email = USER_EMAIL
                    ),
                    error = null
                ).toResponse()
            } else {
                LoginData(
                    user = null,
                    error = ErrorData(
                        id = ERROR_ID,
                        message = ERROR_MESSAGE
                    )
                ).toResponse()
            }
        }

    companion object {
        private const val USER_ID = 1
        private const val USER_NAME = "Gaspar"
        private const val USER_EMAIL = "gabriel@gmail.com"
        private const val USER_PASSWORD = "123456789"

        private const val ERROR_ID = 1
        private const val ERROR_MESSAGE = "Usuário não encontrado."
    }
}