package com.github.tools.interfaces

import android.content.Context
import android.graphics.Bitmap

interface SaveImgCallback {
    fun save(context: Context,bitmap: Bitmap):Boolean
}