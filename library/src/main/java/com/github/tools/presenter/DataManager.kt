package com.github.tools.presenter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

object DataManager {

    //build context instantiation
    private var context: Context? = null

    //build img instantiation
    private var IMG: String? = null

    /**
     * initialize the context
     * @param context context
     * @return itself
     */
    @JvmStatic
    fun init(context: Context): DataManager {
        this.context = context
        this.IMG = context.filesDir.path + "/IMG"
        return this
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
        for (i in 0..listFiles.size - 1) {
            if (listFiles[i].isDirectory) {
                deleteFile(path + "/" + listFiles[i].name)
                delete(path + "/" + listFiles[i].name)
            } else {
                listFiles[i].delete()
            }
        }
    }

    /**
     * after the call, delete all files containing the cache word directory under the current application built-in path
     */
    @JvmStatic
    fun deleteAllCache() {
        delete(context!!.cacheDir.path)
        val otherFile = File(context!!.cacheDir.path.replace("/cache", ""))
        for (i in otherFile.listFiles().indices) {
            val name = otherFile.listFiles()[i].name
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
    @JvmStatic
    fun deleteSql() {
        delete(context!!.cacheDir.path.replace("/cache", "") + "/databases")
    }

    /**
     * delete all files in sharedPreferences
     */
    @JvmStatic
    fun deleteShared() {
        delete(context!!.cacheDir.path.replace("/cache", "") + "/shared_prefs")
    }

    /**
     * save the bitmap to the built-in storage and return whether the save is successful
     * @param bitmap img bitmap
     * @param image_format img  format
     */
    @JvmStatic
    fun saveImage(bitmap: Bitmap, image_format: Bitmap.CompressFormat): Boolean {
        if (!File(IMG).exists()) {
            run outSide@{
                val mkdir = File(IMG).mkdir()
                if (mkdir) return@outSide
            }
        }
        val filePic = File(
            IMG + "/"
                    + System.currentTimeMillis() / 1000 + ".$image_format"
        )
        val recording =
            IMG + "/" + (System.currentTimeMillis() / 1000).toString() + ".$image_format"
        val fos = FileOutputStream(filePic)
        bitmap.compress(image_format, 90, fos)
        fos.flush()
        fos.close()
        if (!File(recording).exists()) return false
        return true
    }

    /**
     * get the set of bitmaps saved in /img
     * @return picture bitmap array
     */
    @JvmStatic
    fun getImgAllArray(): List<Bitmap>? {
        val file = File(IMG)
        if (!file.exists()) return null
        val listFiles = file.listFiles()
        val arrayList = ArrayList<Bitmap>()
        for (element in listFiles) {
            val fis = FileInputStream(element.absoluteFile)
            val bitmap = BitmapFactory.decodeStream(fis)
            arrayList.add(bitmap)
        }
        return arrayList
    }
}