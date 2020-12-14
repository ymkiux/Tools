package com.github.tools.operating

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

object L {


    private var context: Context = com.github.tools.data.Context.getContext()

    //gets an instance of the current app activity display
    private var theCurrentActivity: Activity = com.github.tools.data.Context.getActivity()

    /**
     * package toast display
     * theCurrentActivity:to avoid being referenced within subThreads that cause program exceptions
     * @param msg msg
     * @param time default minimum time display
     */
    @JvmStatic
    fun t(msg: String?, time: Int = 0) {
        theCurrentActivity.runOnUiThread {
            val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            toast.setText(msg)
            toast.show()
        }
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