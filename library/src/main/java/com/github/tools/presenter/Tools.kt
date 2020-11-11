package com.twst.presenter

import android.graphics.Bitmap
import android.util.Log
import com.twst.task.DownFileTask
import com.twst.task.GetBitmapTask

object Tools {

    /**
     * call to get a callback to the bitmap
     * @param url url link
     */
    @JvmStatic
    fun getBitMap(url: String): Bitmap {
        return GetBitmapTask.getBitmap(url)
    }

    /**
     * call the download callback
     * @param url url file link
     */
    @JvmStatic
    fun downFile(url: String) {
        DownFileTask.downCall(url)
    }

    /**
     *A prompt message
     * @param tag Label
     * @param msg Tips
     */
    @JvmStatic
    fun getLogI(tag: String, msg: String) {
        Log.i(
            tag,
            msg
        )
    }

    /**
     *A error prompt message
     * @param tag Label
     * @param msg Tips
     */
    @JvmStatic
    fun getLogR(tag: String, msg: String) {
        Log.e(
            tag,
            msg
        )
    }

}