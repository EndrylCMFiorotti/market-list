package com.study.marketlist.network.exceptions

data class RedirectException(
    override val message: String?,
    val code: Int,
) : Exception(message)