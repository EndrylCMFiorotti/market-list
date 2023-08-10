package com.study.marketlist.network.exceptions

import java.lang.Exception

data class ServerException(
    override val message: String?,
    val code: Int,
) : Exception(message)
