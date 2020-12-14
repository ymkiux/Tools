package com.github.tools.interfaces

import android.os.Message

interface HandlerListener {
    //call back the contents of the handler msg communication
    fun handleMessage(msg: Message)
}