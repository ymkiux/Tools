package com.github.tools


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type


object Json {
    /**
     * Get content from the jason field key
     * @param text The string of the object type
     * @param key key
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun getJsonObject(text: String, key: String): Any? {
        if (!text.startsWith("{") && !text.endsWith("}")) return throw IllegalArgumentException("Strings are not jason object types")
        val jsonObject = JSONObject(text)
        val value = jsonObject.get(key)
        if (value is String) return value.toString()
        if (value is Int) return value.toInt()
        if (value is Double) return value.toDouble()
        if (value is Float) return value.toFloat()
        if (value is JSONArray) return value as JSONArray
        if (value is JSONObject) return value as JSONObject
        return null
    }

    /**
     * Convert it into a Javabean type with a johnson string
     * @param text The string of the object type
     * @param t Data custom Javabean
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun <T> getJsonObject(text: String, t: Class<T>): T {
        if (!text.startsWith("{") && !text.endsWith("}")) return throw IllegalArgumentException("Strings are not jason object types")
        return Gson().fromJson(text, t)
    }


    /**
     * Converts an array instance from the <T> a list string
     * @param text The string of the array type
     * @param t Data custom Javabean
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun <T> getJsonArrayList(text: String, t: T): List<T> {
        if (!text.startsWith("[") && !text.endsWith("]")) return throw IllegalArgumentException("Strings are not jason array types")
        val type: Type = object : TypeToken<List<T>?>() {}.type
        return Gson().fromJson(JSONArray(text).toString(), type)
    }
}