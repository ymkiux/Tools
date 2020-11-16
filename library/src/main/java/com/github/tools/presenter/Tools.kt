package com.github.tools.presenter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.github.tools.task.DownFileTask
import com.github.tools.task.GetBitmapTask
import com.github.tools.task.SaveImgTask

object Tools {
    private var context: Context? = null

    @JvmStatic
    fun init(context: Context): Tools {
        this.context = context
        return this
    }

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
     * save bitmap to external public directory
     * @return return true if the save is successful
     */
    @JvmStatic
    fun saveImg(bitmap: Bitmap): Boolean {
        if (context == null) return throw NullPointerException("no initialization operation")
        return SaveImgTask.save(context!!, bitmap)
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