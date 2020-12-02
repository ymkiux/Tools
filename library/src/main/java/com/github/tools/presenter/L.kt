package com.github.tools.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast

object L {
    private var context: Context = com.github.tools.data.Context.getContext()

    /**
     * package toast display
     * @param msg msg
     * @param time default minimum time display
     */
    fun t(msg: String?, time: Int = 0) {
        Toast.makeText(context, msg, time).show()
    }

    /**
     * A prompt message
     * @param tag Label
     * @param msg Tips
     */
    fun getLogI(tag: String, msg: String) {
        Log.i(
            tag,
            msg
        )
    }
}