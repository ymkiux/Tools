package com.twst.interfaces

import android.graphics.Bitmap

interface GetBitmapCallback {
    //gets a bitmap callback for the picture
    fun  getBitmap(url: String): Bitmap
}