package com.github.tools.interfaces

interface RightsManagementCallback {
    /** the action that needs to be done after the permission is obtained is called back to the consumer **/
    fun doWork()
}