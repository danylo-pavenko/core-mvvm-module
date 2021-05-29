package com.dansdev.core.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.from(data: String?): T? {
    return data?.let { if (it.isEmpty()) null else fromJson(it, T::class.java) }
}
