package com.github.tools.task

import android.os.Environment
import com.github.tools.interfaces.DownFileCallback
import com.github.tools.presenter.OkGo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object DownFileTask : DownFileCallback {

    /**
     *Download the file via url to the /Download directory
     * @param url url file link
     */
    override fun downCall(url: String) {
        OkGo.get().url(url).build()!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                var len = 0
                val buf = ByteArray(2048)
                val externalStoragePublicDirectory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val f = File(externalStoragePublicDirectory.toString())
                val substring = url.substring(url.lastIndexOf('/') + 1);
                val file = File(f, substring)
                val fos = FileOutputStream(file)
                while (response.body!!.byteStream().read(buf).also { len = it } != -1) {
                    fos.write(buf, 0, len)
                }
                fos.flush()
                fos.close()
            }
        })
    }
}