package com.study.marketlist.network.exceptions

data class UnknownResponseException(
    override val message: String?,
    val code: Int,
) : Exception(message)