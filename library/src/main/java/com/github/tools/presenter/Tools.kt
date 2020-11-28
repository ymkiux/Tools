package com.github.tools.presenter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.github.tools.interfaces.ConfirmCallback
import com.github.tools.interfaces.HandlePostBack
import com.github.tools.task.tools.DownFileTask
import com.github.tools.task.tools.GetBitmapTask

object Tools {
    private var context: Context = com.github.tools.data.Context.getContext()


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
     * @param IMG_TAG create internal and external image storage location identification
     * @return return true if the save is successful
     */
    fun saveImg(
        bitmap: Bitmap,
        IMG: String = context.filesDir.path + "/IMG",
        IMG_TAG: Boolean = false
    ): Boolean {
        return DataManager.saveImage(bitmap, IMG, IMG_TAG)
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
     * effectively solve the repeated construction of alertDialog custom layout
     * @param id layout format:R.layout.xxx
     * @param confirmCallback click to implement this interface to customize the operation events
     * @return  a view so that the controls can be operated by the user
     */
    fun showAlertDialog(id: Int, confirmCallback: ConfirmCallback): View {
        var show: AlertDialog? = null
        val customizeDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        customizeDialog.setCancelable(false)
        val infoview: View = LayoutInflater.from(context).inflate(
            id,
            null, false
        )
        customizeDialog.setView(infoview)
        customizeDialog.setPositiveButton(
            "确定"
        ) { dialog, which ->
            confirmCallback.doWork()
            show!!.dismiss()
        }
        show = customizeDialog.show()
        return infoview
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