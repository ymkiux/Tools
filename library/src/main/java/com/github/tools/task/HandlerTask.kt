package com.github.tools.task

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.github.tools.interfaces.HandlerListener

class HandlerTask(handlerListener: HandlerListener) {
    //the normal encapsulation handler implementation mechanism does this by notifying the following callbacks via sendMessage
    val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            handlerListener.handleMessage(msg)
        }
    }
}