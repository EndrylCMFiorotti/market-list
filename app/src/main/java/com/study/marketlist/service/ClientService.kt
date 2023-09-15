package com.study.marketlist.service

import com.study.marketlist.network.data.ErrorData
import com.study.marketlist.network.data.LoginData
import com.study.marketlist.network.data.UserData

open class ClientService {
    open fun login(email: String, password: String): LoginData {
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
        const val USER_ID = 1
        const val USER_NAME = "Gaspar"
        const val USER_EMAIL = "gabriel@gmail.com"
        const val USER_PASSWORD = "123456789"

        const val ERROR_ID = 1
        const val ERROR_MESSAGE = "Usuário não encontrado."
    }
}