package com.study.marketlist.repository

import com.study.marketlist.network.ext.toResponse
import com.study.marketlist.network.handler.requestHandler
import com.study.marketlist.network.response.LoginResponse
import com.study.marketlist.network.wrapper.ResultWrapper
import com.study.marketlist.service.ClientService

class UserRepository(private val clientService: ClientService) {
    suspend fun checkLogin(email: String, password: String): ResultWrapper<LoginResponse> =
        requestHandler {
            clientService.login(
                email = email,
                password = password
            ).toResponse()
        }
}