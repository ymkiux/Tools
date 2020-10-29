package com.github.tools

import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

interface Callback {
    /**
     * Called when the HTTP response was successfully returned by the remote server
     */
    @Throws(IOException::class)
    fun success(responseBody: ResponseBody?)

    /**
     * Called when the request could not be executed due to cancellation
     */
    fun error(e: IOException)
}