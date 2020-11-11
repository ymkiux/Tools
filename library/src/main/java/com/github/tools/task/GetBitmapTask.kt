package com.twst.task

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import com.github.tools.OkGo
import com.twst.interfaces.GetBitmapCallback

object GetBitmapTask : GetBitmapCallback {
    /**
     * Get bitmaps from the picture url
     * Suitable for situations where the amount of data requested by the network is small
     * @param url url link
     */
    override fun getBitmap(url: String): Bitmap {
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val byteStream = OkGo.get().url(url).build()!!.execute().body!!.byteStream()
        return BitmapFactory.decodeStream(byteStream)
    }

}