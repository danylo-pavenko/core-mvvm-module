package com.dansdev.core.api.util

import io.paperdb.Paper
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import timber.log.Timber
import java.lang.Exception

private const val COOKIE_STORAGE = "cookie_storage"
private const val COOKIES = "key_data_cookies"

class CookieJarHandler: CookieJar {

    private val book = Paper.book(COOKIE_STORAGE)

    override fun loadForRequest(url: HttpUrl): List<Cookie> = readCookies()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        try {
            val currentCookies = readCookies().toMutableList()
            cookies.forEach { cookie ->
                val hasCookie = currentCookies.firstOrNull { it.name == cookie.name } != null
                if (!hasCookie) currentCookies.add(cookie)
            }
            book.write(COOKIES, currentCookies)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun readCookies(): List<Cookie> = book.read(COOKIES, emptyList())
}
