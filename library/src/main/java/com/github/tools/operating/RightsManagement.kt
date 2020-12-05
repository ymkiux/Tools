package com.github.tools.operating

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.github.tools.interfaces.RightsManagementCallback

object RightsManagement {

    private val REQUEST_EXTERNAL_STORAGE = 1

    private val PERMISSIONS_STORAGE = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )

    @JvmStatic
    fun readAndWritePermissions(
        activity: Activity?,
        rightsManagementCallback: RightsManagementCallback
    ) {
        try {
            val permission = ActivityCompat.checkSelfPermission(
                activity!!,
                "android.permission.WRITE_EXTERNAL_STORAGE"
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            } else {
                rightsManagementCallback.doWork()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}