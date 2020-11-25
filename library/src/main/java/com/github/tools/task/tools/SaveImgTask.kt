package com.github.tools.task.tools

import android.content.Context
import android.graphics.Bitmap
import com.github.tools.interfaces.SaveImgCallback
import com.github.tools.presenter.DataManager


object SaveImgTask : SaveImgCallback {
    override fun save(context: Context, bitmap: Bitmap): Boolean {
        return DataManager.init(context).externalImageLibrary().saveImage(bitmap)
    }
}