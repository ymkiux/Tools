package com.github.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import com.github.tools.Tools.getLog
import okhttp3.*
import okhttp3.Callback
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


object OkGo {

    //get or post method identification
    private var Method: Boolean = false

    //carrying parameters
    private var hashMapParam =
        HashMap<String, String>()

    //customize the head
    private var hashMapHead =
        HashMap<String, String>()

    //the link you need to access
    private var url: String? = null

    /**
     * get method
     */
    @JvmStatic
    fun get(): OkGo {
        return this
    }

    /**
     * post method
     */
    @JvmStatic
    fun post(): OkGo {
        Method = true
        return this
    }

    /**
     * the link you need to access
     * @param url link
     */
    @JvmStatic
    fun url(url: String): OkGo {
        this.url = url
        return this
    }

    /**
     * add params
     * @param key param key
     * @param value param value
     */
    @JvmStatic
    fun addParams(key: String, value: String): OkGo {
        hashMapParam.put(key, value)
        return this

    }

    /**
     * add a head
     * @param hashMapHeadKey head key
     * @param hashMapHeadValue head value
     */
    @JvmStatic
    fun addHeader(hashMapHeadKey: String, hashMapHeadValue: String): OkGo {
        hashMapHead.put(hashMapHeadKey, hashMapHeadValue)
        return this
    }

    /**
     * no ginseng custom head get/post method
     */
    @JvmStatic
    fun build(): Call? {
        when (Method) {
            false -> {
                val startTime = System.currentTimeMillis()
                when (hashMapParam.size) {
                    0 -> {
                        when (hashMapHead.size) {
                            0 -> {

                                val client: OkHttpClient = OkHttpClient.Builder()
                                    .connectTimeout(4, TimeUnit.SECONDS)
                                    .readTimeout(0, TimeUnit.SECONDS).build()
                                val request: Request = Request.Builder()
                                    .url(this.url!!)
                                    .build()
                                val newCall = client.newCall(request)
                                val endTime = System.currentTimeMillis()
                                getLog(
                                    this@OkGo.toString()
                                        .substring(0, this@OkGo.toString().indexOf("@"))
                                        .substring(this@OkGo.toString().lastIndexOf(".") + 1),
                                    "--> GET\t" + url + "\t" + (endTime - startTime) + "ms"
                                )
                                return newCall
                            }
                            1 -> {
                                var keys: String? = null
                                var value: String? = null
                                for (key in hashMapHead.keys) {
                                    if (hashMapHead[key] != null) {
                                        keys = key
                                        value = hashMapHead[key]!!
                                    }
                                }
                                val client: OkHttpClient = OkHttpClient.Builder()
                                    .connectTimeout(4, TimeUnit.SECONDS)
                                    .readTimeout(0, TimeUnit.SECONDS).build()
                                val request: Request = Request.Builder()
                                    .addHeader(keys!!, value!!)
                                    .url(this.url!!)
                                    .build()
                                val newCall = client.newCall(request)
                                val endTime = System.currentTimeMillis()
                                getLog(
                                    this@OkGo.toString()
                                        .substring(0, this@OkGo.toString().indexOf("@"))
                                        .substring(this@OkGo.toString().lastIndexOf(".") + 1),
                                    "--> GET\t" + url + "\t" + (endTime - startTime) + "ms"
                                )
                                return newCall
                            }
                        }
                    }
                }
            }
            true -> {
                when (hashMapParam.size) {
                    0 -> {
                    }
                    else -> {
                        val startTime = System.currentTimeMillis()
                        when (hashMapHead.size) {
                            0 -> {
                                val client: OkHttpClient = OkHttpClient.Builder()
                                    .connectTimeout(4, TimeUnit.SECONDS)
                                    .readTimeout(0, TimeUnit.SECONDS).build()
                                val urlBuilder = MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                for (key in hashMapParam.keys) {
                                    if (hashMapParam[key] != null) {
                                        urlBuilder.addFormDataPart(key, hashMapParam[key]!!)
                                    }
                                }
                                val request: Request = Request.Builder()
                                    .url(url!!)
                                    .post(urlBuilder.build())
                                    .build()
                                val newCall = client.newCall(request)
                                val endTime = System.currentTimeMillis()
                                getLog(
                                    this@OkGo.toString()
                                        .substring(0, this@OkGo.toString().indexOf("@"))
                                        .substring(this@OkGo.toString().lastIndexOf(".") + 1),
                                    "--> POST\t" + url + "\t" + (endTime - startTime) + "ms"
                                )
                                return newCall
                            }
                            1 -> {
                                var keys: String? = null
                                var value: String? = null
                                for (key in hashMapHead.keys) {
                                    if (hashMapHead[key] != null) {
                                        keys = key
                                        value = hashMapHead[key]!!
                                    }
                                }
                                val client: OkHttpClient = OkHttpClient.Builder()
                                    .connectTimeout(4, TimeUnit.SECONDS)
                                    .readTimeout(0, TimeUnit.SECONDS).build()
                                val urlBuilder = MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                for (key in hashMapParam.keys) {
                                    if (hashMapParam[key] != null) {
                                        urlBuilder.addFormDataPart(key, hashMapParam[key]!!)
                                    }
                                }
                                val request: Request = Request.Builder()
                                    .url(url!!)
                                    .addHeader(keys!!, value!!)
                                    .post(urlBuilder.build())
                                    .build()
                                val newCall = client.newCall(request)
                                val endTime = System.currentTimeMillis()
                                getLog(
                                    this@OkGo.toString()
                                        .substring(0, this@OkGo.toString().indexOf("@"))
                                        .substring(this@OkGo.toString().lastIndexOf(".") + 1),
                                    "--> POST\t" + url + "\t" + (endTime - startTime) + "ms"
                                )
                                return newCall
                            }
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * Get bitmaps from the picture url
     * Suitable for situations where the amount of data requested by the network is small
     * @param url url link
     */
    @JvmStatic
    fun getBitmap(url: String): Bitmap {
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val byteStream = get().url(url).build()!!.execute().body!!.byteStream()
        return BitmapFactory.decodeStream(byteStream)
    }

    /**
     *Download the file via url to the /Download directory
     * @param url url file link
     */
    @JvmStatic
    fun downFile(url: String) {
        get().url(url).build()!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                Tools.downFile(url, response.body!!.byteStream())
            }
        })
    }
}