package com.github.tools.task

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.widget.ImageView
import com.github.tools.interfaces.GetBitmapCallback
import com.github.tools.interfaces.RightsManagementCallback
import com.github.tools.operating.L
import com.github.tools.operating.RightsManagement
import com.github.tools.presenter.OkGo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @JvmStatic jvmStatic makes method references consistent on java, kotlin
 */
object ToolsTask {

    //gets an instance of the current app activity display
    private var theCurrentActivity: Activity = com.github.tools.data.Context.getActivity()

    /**
     * Get bitmaps from the picture url
     * Suitable for situations where the amount of data requested by the network is small
     * @param url url link
     */
    @Deprecated("the current method is too cumbersome to be deprecated")
    @JvmStatic
    fun getBitmap(url: String, getBitmapCallback: GetBitmapCallback) {
        OkGo.get().url(url).build()!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                getBitmapCallback.getBitmap(BitmapFactory.decodeStream(response.body!!.byteStream()))
            }
        })
    }

    /**
     * Get bitmaps from the picture url
     * @param url url link
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(url: String): Bitmap? {
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val byteStream = OkGo.get().url(url).build()!!.execute().body!!.byteStream()
        return BitmapFactory.decodeStream(byteStream)
    }


    /**
     * determine the status of the ImageView content
     * @param imageView imageView view name
     * @return status
     */
    @JvmStatic
    fun getImgStatus(imageView: ImageView): Boolean {
        var status = false
        try {
            (imageView.drawable as BitmapDrawable).bitmap
            status = true
        } catch (e: Exception) {
            return status
        }
        return status
    }

    /**
     * downFile or downDefinedFile method
     * an exception may occur when using the current method
     * exception one:java.net.UnknownServiceException: CLEARTEXT communication ** not permitted by network security policy
     * the workaround:android:usesCleartextTraffic="true" added with in the application node of manifest
     * exception two:open failed: eacces (Permission denied) added with in the application node of manifest
     * the workaround:android:requestLegacyExternalStorage="true"  after a successful run is added, it can be deleted
     * @param url file download straight chain
     */
    @JvmStatic
    fun downFile(url: String) {
        downDefinedFile(url)
    }

    /**
     * @param file downloaded to the public download directory by default
     */
    @JvmStatic
    fun downDefinedFile(
        url: String,
        file: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    ) {
        RightsManagement.readAndWritePermissions(theCurrentActivity,
            object : RightsManagementCallback {
                override fun doWork() {
                    OkGo.get().url(url).build()!!.enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {}
                        override fun onResponse(call: Call, response: Response) {
                            var len: Int
                            val buf = ByteArray(2048)
                            val externalStoragePublicDirectory = file
                            val f = File(externalStoragePublicDirectory.toString())
                            val substring = url.substring(url.lastIndexOf('/') + 1);
                            val file = File(f, substring)
                            val fos = FileOutputStream(file)
                            while (response.body!!.byteStream().read(buf).also { len = it } != -1) {
                                fos.write(buf, 0, len)
                            }
                            fos.flush()
                            fos.close()
                            L.t("下载成功")
                        }
                    })
                }
            })
    }

    /**
     * save pictures from bitmap to the local public picture catalog refresh the library use boolean to display the save status
     * @param bitmap picture bitmap
     * @return save bitmap status
     */
    @JvmStatic
    fun saveImg(bitmap: Bitmap): Boolean {
        var time: String? = null
        var status: Boolean = false
        RightsManagement.readAndWritePermissions(theCurrentActivity,
            object : RightsManagementCallback {
                override fun doWork() {
                    try {
                        val cr = theCurrentActivity.contentResolver
                        time = System.currentTimeMillis().toString()
                        MediaStore.Images.Media.insertImage(
                            cr,
                            bitmap,
                            time,
                            ""
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    MediaScannerConnection.scanFile(
                        theCurrentActivity, arrayOf(
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES
                            ).absolutePath
                        ), null, null
                    )

                    val checkFileName = com.github.tools.operating.File().isExists(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES
                        ).absolutePath + "/$time.jpg"
                    )
                    if (checkFileName) status = true
                }
            })
        return status
    }
}