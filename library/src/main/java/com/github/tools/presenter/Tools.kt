package com.github.tools.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import com.github.tools.interfaces.ConfirmCallback
import com.github.tools.interfaces.HandlePostBack
import com.github.tools.task.tools.DownFileTask
import com.github.tools.task.tools.GetBitmapTask
import java.util.*

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
        IMG_TAG: Boolean = false,
        IMG: String = context.filesDir.path + "/IMG"
    ): Boolean {
        return DataManager.saveImage(bitmap, IMG_TAG, IMG)
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
        customizeDialog.setCancelable(true)
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
     * get the number of activities in the current stack
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    fun getTheNumberInTheStack(): Int {
        val list: MutableList<Activity> =
            ArrayList()
        try {
            val activityThread =
                Class.forName("android.app.ActivityThread")
            val currentActivityThread =
                activityThread.getDeclaredMethod("currentActivityThread")
            currentActivityThread.isAccessible = true
            val activityThreadObject = currentActivityThread.invoke(null)
            val mActivitiesField =
                activityThread.getDeclaredField("mActivities")
            mActivitiesField.isAccessible = true
            val mActivities =
                mActivitiesField[activityThreadObject] as Map<Any, Any>
            for ((_, value) in mActivities) {
                val activityClientRecordClass: Class<*> = value.javaClass
                val activityField =
                    activityClientRecordClass.getDeclaredField("activity")
                activityField.isAccessible = true
                val o = activityField[value]
                list.add(o as Activity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list.size
    }
}