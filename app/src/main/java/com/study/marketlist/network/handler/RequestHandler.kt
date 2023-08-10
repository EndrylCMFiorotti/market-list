package com.study.marketlist.network.handler

import com.study.marketlist.network.exceptions.ClientException
import com.study.marketlist.network.exceptions.RedirectException
import com.study.marketlist.network.exceptions.ServerException
import com.study.marketlist.network.exceptions.UnknownResponseException
import com.study.marketlist.network.wrapper.ResultWrapper
import java.net.HttpRetryException

suspend fun <T> requestHandler(function: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(function())
    } catch (e: HttpRetryException) {
        ResultWrapper.Error(
            when (e.responseCode()) {
                in 300..399 -> RedirectException(code = e.responseCode(), message = e.message)
                in 400..499 -> ClientException(code = e.responseCode(), message = e.message)
                in 500..599 -> ServerException(code = e.responseCode(), message = e.message)
                else -> UnknownResponseException(code = e.responseCode(), message = e.message)
            }
        )
    }
}