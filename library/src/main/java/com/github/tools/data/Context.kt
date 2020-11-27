package com.github.tools.data

import android.content.Context

object Context {

    private var context: Context? = null

    /**
     * initialization context
     * @param context context
     */
    fun init(context: Context) {
        this.context = context
    }

    /**
     * get context
     * @return context
     */
    fun getContext(): Context {
        if (context == null) throw NullPointerException("no initialization operation")
        return context!!
    }
}