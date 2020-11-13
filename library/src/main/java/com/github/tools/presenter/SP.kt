package com.github.tools.presenter

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SP {

    //define the sp-stored value file name
    const val fileName = "share_preference"

    /**
     * set the content to key
     * @param key key
     * @param value key value
     */
    @JvmStatic
    fun Context.set(key: String, value: Any) {
        val sp: SharedPreferences =
            this.getSharedPreferences(fileName, MODE_PRIVATE)
        when (value) {
            is String -> sp.edit().putString(key, value).apply()
            is Int -> sp.edit().putInt(key, value).apply()
            is Float -> sp.edit().putFloat(key, value).apply()
            is Boolean -> sp.edit().putBoolean(key, value).apply()
            is Long -> sp.edit().putLong(key, value).apply()
            else -> throw ClassCastException("The current type is abnormal")
        }
    }

    /**
     * get the content to key
     * @param key key
     * @param value key value
     */
    @JvmStatic
    fun Context.get(key: String, type: Any): Any? {
        val sp: SharedPreferences =
            this.getSharedPreferences(fileName, MODE_PRIVATE)
        return when (type) {
            is String -> sp.getString(key, "")
            is Int -> sp.getInt(key, 0)
            is Float -> sp.getFloat(key, 0f)
            is Boolean -> sp.getBoolean(key, false)
            is Long -> sp.getLong(key, 1)
            else -> throw ClassCastException("The current type is abnormal")
        }
    }

    /**
     * detect the presence of keys
     * @param key key
     */
    @JvmStatic
    fun Context.checkKey(key: String): Boolean {
        val sp: SharedPreferences =
            this.getSharedPreferences(fileName, MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * delete the content for key
     * @param key key
     */
    @JvmStatic
    fun Context.clearKey(key: String) {
        val sp: SharedPreferences =
            this.getSharedPreferences(fileName, MODE_PRIVATE)
        sp.edit().remove(key).apply()
    }

    /**
     * delete the contents of all keys in the current file
     */
    @JvmStatic
    fun Context.clearAll() {
        val sp: SharedPreferences =
            this.getSharedPreferences(fileName, MODE_PRIVATE)
        sp.edit().clear().apply()
    }
}