package com.github.tools.presenter

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.github.tools.interfaces.HandlePostBack
import com.github.tools.task.tools.DownFileTask
import com.github.tools.task.tools.GetBitmapTask
import com.github.tools.task.tools.SaveImgTask

object Tools {
    private var context: Context? = null

    /**
     * initialization context
     * @return this
     */
    fun init(context: Context): Tools {
        this.context = context
        return this
    }

    /**
     * call to get a callback to the bitmap
     * @param url url link
     */
    fun getBitMap(url: String): Bitmap {
        return GetBitmapTask.getBitmap(url)
    }

    /**
     * call the download callback
     * @param url url file link
     */
    fun downFile(url: String) {
        DownFileTask.downCall(url)
    }

    /**
     * save bitmap to external public directory
     * @return return true if the save is successful
     */
    fun saveImg(bitmap: Bitmap): Boolean {
        if (context == null) throw NullPointerException("no initialization operation")
        return SaveImgTask.save(context!!, bitmap)
    }

    /**
     * used in the main thread by default
     * perform related operations by implementing this method
     * @param handlePostBack callback interface
     * @param delayMillis delay time
     * @param threadTag the thread is switched to be in a child thread state
     */
    fun handlerPostDelayed(
        handlePostBack: HandlePostBack,
        delayMillis: Long,
        threadTag: Boolean = false
    ) {
        when (threadTag) {
            true -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    handlePostBack.doWork()
                }, delayMillis)
            }
            false -> {
                Handler().postDelayed({
                    handlePostBack.doWork()
                }, delayMillis)
            }
        }
    }


    /**
     * A prompt message
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