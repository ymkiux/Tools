package com.github.tools.presenter

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.*


/**
 * The current SharedPreferences method has been replaced by DataStore
 * For details, please refer to https://developer.android.com/topic/libraries/architecture/datastore
 */
object SP {

    //define the sp-stored value file name
    const val fileName = "share_preference"

    //initialization context
    private var context: Context? = null

    @Deprecated("recommend to use preferences dataStore api")
    fun init(): SP {
        this.context = context
        return this
    }


    /**
     * set the content to key
     * @param key key
     * @param value key value
     */
    @Deprecated("recommend to use preferences dataStore api")
    fun set(key: String, value: Any) {
        if (context == null) throw NullPointerException("no initialization operation")
        val sp: SharedPreferences =
            context!!.getSharedPreferences(fileName, MODE_PRIVATE)
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
    @Deprecated("recommend to use preferences dataStore api")
    fun get(key: String, type: Any): Any? {
        if (context == null) throw NullPointerException("no initialization operation")
        val sp: SharedPreferences =
            context!!.getSharedPreferences(fileName, MODE_PRIVATE)
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
     * get the specified key value collection
     * @param name key-value file name
     * @param mode operation mode Context.MODE_PRIVATE:the default operation mode, which means that the file is private data
     * @return the set of key-value pairs in the specified key-value file name
     */
    fun spGetMap(name: String, mode: Int = Context.MODE_PRIVATE): HashMap<String, Any?> {
        if (context == null) throw NullPointerException("no initialization operation")
        val sp = context!!.getSharedPreferences(name, mode)
        val all = sp.all
        val hashMap = HashMap<String, Any?>()
        for (key in all.keys) {
            hashMap.put(key, all[key])
        }
        return hashMap
    }

    /**
     * detect the presence of keys
     * @param key key
     */
    @Deprecated("recommend to use preferences dataStore api")
    fun checkKey(key: String): Boolean {
        if (context == null) throw NullPointerException("no initialization operation")
        val sp: SharedPreferences =
            context!!.getSharedPreferences(fileName, MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * delete the content for key
     * @param key key
     */
    @Deprecated("recommend to use preferences dataStore api")
    fun clearKey(key: String) {
        if (context == null) throw NullPointerException("no initialization operation")
        val sp: SharedPreferences =
            context!!.getSharedPreferences(fileName, MODE_PRIVATE)
        sp.edit().remove(key).apply()
    }

    /**
     * delete the contents of all keys in the current file
     */
    @Deprecated("recommend to use preferences dataStore api")
    fun clearAll() {
        if (context == null) throw NullPointerException("no initialization operation")
        val sp: SharedPreferences =
            context!!.getSharedPreferences(fileName, MODE_PRIVATE)
        sp.edit().clear().apply()
    }
}