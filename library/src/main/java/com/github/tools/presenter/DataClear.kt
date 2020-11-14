package com.github.tools.presenter

import android.content.Context
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern

object DataClear {

    //build context instantiation
    private var context: Context? = null

    /**
     * initialize the context
     * @param context context
     * @return itself
     */
    @JvmStatic
    fun init(context: Context): DataClear {
        this.context = context
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
}