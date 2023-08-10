package com.study.marketlist.util

fun String.isValidEmail() =
    Regex("^([a-zA-Z0-9_.]+)@([a-zA-Z0-9_.]+)\\.([a-zA-Z]{2,6})$").matches(this)