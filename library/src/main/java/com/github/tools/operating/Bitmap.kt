package com.github.tools.operating

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object Bitmap {

    /**
     * save the picture locally
     * @param bmp img bitmap
     * @param f file name
     */
    fun saveBitmapAsPng(bmp: Bitmap, f: File?) {
        try {
            val out = FileOutputStream(f)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * check the save status
     * @param time file name
     * @return save status
     */
    fun checkSaveStatus(time: String): Boolean {
        val checkFileName = com.github.tools.operating.File().isExists(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ).absolutePath + "/$time"
        )
        if (checkFileName) return true
        return false
    }
}