package com.github.tools.operating

import java.io.File

class File {

    /**
     * call the method of deleting files in the current folder before deleting
     * @param path table of contents path
     */
    private fun deleteFolder(path: String) {
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
                deleteFolder(path + "/" + listFiles[i].name)
            } else {
                listFiles[i].delete()
            }
        }
    }

    /**
     * check if the file exists
     * @param path table of contents path
     * @return does it exist
     */
    private fun checkFile(path: String): Boolean {
        return File(path).exists()
    }

}