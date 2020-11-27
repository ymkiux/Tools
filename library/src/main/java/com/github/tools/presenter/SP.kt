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

    private var context: Context = com.github.tools.data.Context.getContext()

    //initialize the current collection
    private var hashMap: HashMap<String, Any> = HashMap<String, Any>()

    /**
     * add param
     * @param
     */
    @Deprecated("the recommended method is in line 10 of this category")
    fun add(key: String, value: Any): SP {
        hashMap.put(key, value)
        return this
    }


    /**
     * set the content to key
     * @param key key
     * @param value key value
     * @param fileName define the sp-stored value file name
     */
    @Deprecated("the recommended method is in line 10 of this category")
    fun set(fileName: String = "share_preference") {
        if (hashMap.size == 0) throw NullPointerException("no param")
        val sp: SharedPreferences = context.getSharedPreferences(fileName, MODE_PRIVATE)
        for (key in hashMap.keys) {
            val value = hashMap[key]
            when (value) {
                is String -> sp.edit().putString(key, value).apply()
                is Int -> sp.edit().putInt(key, value).apply()
                is Float -> sp.edit().putFloat(key, value).apply()
                is Boolean -> sp.edit().putBoolean(key, value).apply()
                is Long -> sp.edit().putLong(key, value).apply()
                else -> throw ClassCastException("The current type is abnormal")
            }
        }
    }


    /**
     * get the content to key
     * @param key key
     * @param value key value
     */
    @Deprecated("the recommended method is in line 10 of this category")
    fun get(key: String, fileName: String = "share_preference"): Any? {
        val sp: SharedPreferences =
            context.getSharedPreferences(fileName, MODE_PRIVATE)
        return sp.all[key]
    }


    /**
     * detect the presence of keys
     * @param key key
     * @return returns whether the value corresponding to this key exists
     */
    @Deprecated("the recommended method is in line 10 of this category")
    fun checkKey(key: String, fileName: String = "share_preference"): Boolean {
        val sp: SharedPreferences =
            context.getSharedPreferences(fileName, MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * delete the content for key
     * @param key key
     */
    @Deprecated("the recommended method is in line 10 of this category")
    fun deleteKey(key: String, fileName: String = "share_preference"): Boolean {
        val sp: SharedPreferences =
            context.getSharedPreferences(fileName, MODE_PRIVATE)
        sp.edit().remove(key).apply()
        return sp.all.get(key) == null
    }

    /**
     * delete the contents of all keys in the current file
     */
    @Deprecated("the recommended method is in line 10 of this category")
    fun deleteAll(fileName: String = "share_preference"): Boolean {
        val sp: SharedPreferences =
            context.getSharedPreferences(fileName, MODE_PRIVATE)
        sp.edit().clear().apply()
        return sp.all.isEmpty()
    }
}