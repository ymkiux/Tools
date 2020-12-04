package com.github.tools.presenter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Double
import java.math.BigDecimal
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object DataManager {

    //build context instantiation
    private var context: Context = com.github.tools.data.Context.getContext()

    /**
     * after the call, delete all files containing the cache word directory under the current application built-in path
     */
    fun deleteAllCache() {
        delete(context.cacheDir.path)
        val otherFile = File(context.cacheDir.path.replace("/cache", ""))
        for (i in otherFile.listFiles()!!.indices) {
            val name = otherFile.listFiles()!![i].name
            val p: Pattern = Pattern.compile("^[A-Za-z0-9].*.cache")
            val m: Matcher = p.matcher(name)
            while (m.find()) {
                delete(m.group())
            }
        }
    }

    /**
     * delete all files in the database
     */
    fun deleteSql() {
        delete(context.cacheDir.path.replace("/cache", "") + "/databases")
    }

    /**
     * delete all files in sharedPreferences
     */
    fun deleteShared() {
        delete(context.cacheDir.path.replace("/cache", "") + "/shared_prefs")
    }

    /**
     * save the bitmap to the built-in storage and return whether the save is successful
     * @param bitmap img bitmap
     * @param IMG_TAG create internal and external image storage location identification
     * @return return true if the save is successful
     */
    fun saveImage(
        bitmap: Bitmap,
        IMG_TAG: Boolean = false,
        IMG: String
    ): Boolean {
        if (!File(IMG).exists()) {
            run outSide@{
                val mkdir = File(IMG).mkdir()
                if (mkdir) return@outSide
            }
        }
        val filePic = File(
            IMG + "/"
                    + System.currentTimeMillis() / 1000 + "." + Bitmap.CompressFormat.PNG
        )
        val recording =
            IMG + "/" + (System.currentTimeMillis() / 1000).toString() + "." + Bitmap.CompressFormat.PNG
        val fos = FileOutputStream(filePic)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos.flush()
        fos.close()
        if (IMG_TAG) {
            val uri =
                Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES))
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = uri
            context.sendBroadcast(intent)
        }
        if (!File(recording).exists()) return false
        return true
    }

    /**
     * get the set of bitmaps saved in /img
     * @return picture bitmap array
     */
    @JvmStatic
    fun getImgAllArray(IMG: String = context.filesDir.path + "/IMG"): List<Bitmap>? {
        val file = File(IMG)
        if (!file.exists()) return null
        val listFiles = file.listFiles()
        val arrayList = ArrayList<Bitmap>()
        for (element in listFiles!!) {
            val fis = FileInputStream(element.absoluteFile)
            val bitmap = BitmapFactory.decodeStream(fis)
            arrayList.add(bitmap)
        }
        return arrayList
    }

    /**
     * get the corresponding file size by calling the default cache path to represent the current application default cache size
     * @return return to the default cache
     */
    fun getDefaultCacheSize(): String? {
        var cacheSize: Long = getFolderSize(context.getCacheDir())
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir())
        }
        return getFormatSize(cacheSize)
    }

    /**
     * count the default cache size by traversing the file size in the record directory
     * @param file file path
     * @return returns the default cache size
     */
    private fun getFolderSize(file: File?): Long {
        var size: Long = 0
        if (file != null) {
            val fileList: Array<File> = file.listFiles()
            if (fileList != null && fileList.size > 0) {
                for (i in fileList.indices) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i])
                    } else {
                        size = size + fileList[i].length()
                    }
                }
            }
        }
        return size
    }

    /**
     * get the theoretical default cache value through specific group formatting
     * @param size cache size
     * @return return format cache size
     */
    private fun getFormatSize(size: Long): String? {
        val kiloByte = size / 1024
        val megaByte = kiloByte / 1024
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(megaByte.toDouble().toString())
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString().toString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(Double.toString(gigaByte.toDouble()))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString().toString() + "GB"
        }
        val result4: BigDecimal = BigDecimal.valueOf(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
            .toString() + "TB"
    }


    /**
     * call the method of deleting files in the current folder before deleting
     * @param path table of contents path
     */
    private fun delete(path: String) {
        deleteFile(path)
        File(path).delete()
    }

    /**
     * delete all files (including directories) within the specified path
     * @param path table of contents path
     */
    private fun deleteFile(path: String) {
        val anyFile = File(path)
        if (!anyFile.exists()) return
        val listFiles = anyFile.listFiles()
        for (i in listFiles!!.indices) {
            if (listFiles[i].isDirectory) {
                deleteFile(path + "/" + listFiles[i].name)
                delete(path + "/" + listFiles[i].name)
            } else {
                listFiles[i].delete()
            }
        }
    }
}