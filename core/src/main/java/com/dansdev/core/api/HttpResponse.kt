package com.dansdev.core.api

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}
