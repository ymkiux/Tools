package com.github.tools.task

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import com.github.tools.interfaces.GetBitmapCallback
import com.github.tools.presenter.OkGo

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