package com.github.tools.data

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

object Context {

    private var context: Context? = null

    private var activity: Activity? = null

    /**
     * initialization context
     * @param context context
     */
    @JvmStatic
    fun init(context: Context) {
        this.context = context
        (context as Application).registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStarted(p0: Activity) {

            }

            override fun onActivityDestroyed(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {

            }

            override fun onActivityResumed(p0: Activity) {
                activity = p0
            }
        })
    }

    /**
     * get context
     * @return context
     */
    fun getContext(): Context {
        if (context == null) throw NullPointerException("no initialization operation")
        return context!!
    }

    /**
     * gets the current active instance
     */
    fun getActivity(): Activity {
        return activity!!
    }
}