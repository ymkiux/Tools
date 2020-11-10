package com.github.tools

import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object Tools {

    /**
     * The save data flow operation is called through a subThread
     * @param url url link
     * @param inputStream The data entry stream
     */
    @JvmStatic
    fun downFile(url: String, inputStream: InputStream) {
        Thread(Runnable {
            saveFile(url, inputStream)
        }).start()
    }

    /**
     * saved to the specified directory via a sightread
     * @param url url link
     * @param inputStream The data entry stream
     */
    private fun saveFile(url: String, inputStream: InputStream) {
        var len = 0
        val buf = ByteArray(2048)
        val externalStoragePublicDirectory =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
        val f = File(externalStoragePublicDirectory.toString())
        val substring = url.substring(url.lastIndexOf('/') + 1);
        val file = File(f, substring)
        val fos = FileOutputStream(file)
        while (inputStream.read(buf).also { len = it } != -1) {
            fos.write(buf, 0, len)
        }
        fos.flush()
        fos.close()
    }

    /**
     *A prompt message
     * @param tag Label
     * @param msg Tips
     */
    @JvmStatic
    fun getLog(tag: String, msg: String) {
        Log.i(
            tag,
            msg
        )
    }
}