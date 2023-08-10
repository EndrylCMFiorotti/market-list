package com.study.marketlist.util

import android.content.Context

class ResourceWrapper(private val context: Context) {
    fun getString(resString: Int) = context.getString(resString)
}