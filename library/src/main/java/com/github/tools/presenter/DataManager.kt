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
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object DataManager {

    //build context instantiation
    private var context: Context? = null

    //build img instantiation
    private var IMG: String? = null

    //create internal and external image storage location identification
    private var IMG_TAG: Boolean = false

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
     * using this method means to store the picture in an external public picture directory
     * @param IMG_TAG change storage id
     * @return this class
     */
    @JvmStatic
    fun externalImageLibrary(): DataManager {
        IMG_TAG = true
        return this
    }

    /**
     * after the call, delete all files containing the cache word directory under the current application built-in path
     */
    @JvmStatic
    fun deleteAllCache() {
        if (context == null)  throw NullPointerException("no initialization operation")
        delete(context!!.cacheDir.path)
        val otherFile = File(context!!.cacheDir.path.replace("/cache", ""))
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
    @JvmStatic
    fun deleteSql() {
        if (context == null)  throw NullPointerException("no initialization operation")
        delete(context!!.cacheDir.path.replace("/cache", "") + "/databases")
    }

    /**
     * delete all files in sharedPreferences
     */
    @JvmStatic
    fun deleteShared() {
        if (context == null)  throw NullPointerException("no initialization operation")
        delete(context!!.cacheDir.path.replace("/cache", "") + "/shared_prefs")
    }

    /**
     * save the bitmap to the built-in storage and return whether the save is successful
     * @param bitmap img bitmap
     * @return return true if the save is successful
     */
    @JvmStatic
    fun saveImage(bitmap: Bitmap): Boolean {
        if (context == null)  throw NullPointerException("no initialization operation")
        if (!File(IMG!!).exists()) {
            run outSide@{
                val mkdir = File(IMG!!).mkdir()
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
            context!!.sendBroadcast(intent)
        }
        if (!File(recording).exists()) return false
        return true
    }

    /**
     * get the set of bitmaps saved in /img
     * @return picture bitmap array
     */
    @JvmStatic
    fun getImgAllArray(): List<Bitmap>? {
        if (context == null)  throw NullPointerException("no initialization operation")
        val file = File(IMG!!)
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