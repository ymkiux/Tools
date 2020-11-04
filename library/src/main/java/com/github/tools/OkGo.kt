package com.github.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.StrictMode
import okhttp3.*
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.TimeUnit


object OkGo {
    var url: String? = null

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
     * Post Request
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

    /**
     * Post Request
     * By constructing MultipartBody.Builder, the form submits parameters
     * @param url Request a link
     * @param params Carrying parameters
     * @param headKey Request header key
     * @param headParam Request header value
     * @param callbacks Request a callback
     */
    @JvmStatic
    fun postUrl(
        url: String,
        params: HashMap<String, Any>,
        headKey: String,
        headParam: Any,
        callbacks: Callback
    ) {
        if (params == null) throw NullPointerException("The carrying parameters must not be empty")
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build()
        val urlBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        val list: MutableList<Map<String, Any>> =
            ArrayList<Map<String, Any>>()
        list.add(params)
        for (m in list) {
            for (key in m.keys) {
                if (params[key] != null) {
                    urlBuilder.addFormDataPart(key, params[key]!!.toString())
                }
            }
        }
        val request: Request = Request.Builder()
            .addHeader(headKey.toString(), headParam.toString())
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

    /**
     * Get bitmaps from the picture url
     * Suitable for situations where the amount of data requested by the network is small
     * @param url url link
     */
    @JvmStatic
    fun getBitmap(url: String): Bitmap? {
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        val inputStream = client.newCall(request).execute().body!!.byteStream()
        return BitmapFactory.decodeStream(inputStream)
    }

    /**
     *Download the file via url to the /Download directory
     * @param url url file link
     */
    @JvmStatic
    fun downFile(url: String) {
        this.url = url
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS).build()
        val inputStream = client.newCall(
            Request.Builder()
                .url(url)
                .build()
        ).execute().body!!.byteStream()
        Tools.downFile(inputStream)
    }
}