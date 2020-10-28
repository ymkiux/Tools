package com.github.tools

import okhttp3.*
import java.util.concurrent.TimeUnit

object OkGo {
    @JvmStatic
    fun getUrl(url: String): String {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build()
        val requestBody: RequestBody = FormBody.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val response: Response = client.newCall(request).execute()
        return response.body!!.string()
    }
}