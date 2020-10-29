package com.github.tools
import okhttp3.*
import java.io.IOException
import java.util.HashMap
import java.util.concurrent.TimeUnit


object OkGo {
    /**
     * Encapsulating get no-participation requests
     * @param url Request a link
     * @param callbacks Request a callback
     */
    @JvmStatic
    fun getUrl(url: String, callbacks: Callback) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                callbacks.error(e)
            }

            override fun onResponse(call: Call, response: Response) {
                callbacks.success(response.body)
            }

        })
    }

    /**
     * Post ginseng request
     * By constructing MultipartBody.Builder, the form submits parameters
     * @param url Request a link
     * @param params Carrying parameters
     * @param callbacks Request a callback
     */
    @JvmStatic
    fun postUrl(url: String, params: HashMap<String, String>, callbacks: Callback) {
        if (params == null) throw NullPointerException("The carrying parameters must not be empty")
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build()
        val urlBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        for (key in params.keys) {
            if (params[key] != null) {
                urlBuilder.addFormDataPart(key, params[key]!!)
            }
        }
        val request: Request = Request.Builder()
            .url(url)
            .post(urlBuilder.build())
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                callbacks.error(e)
            }

            override fun onResponse(call: Call, response: Response) {
                callbacks.success(response.body)
            }

        })
    }
}