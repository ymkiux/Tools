package com.github.tools


import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
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
        if (!text.startsWith("{") && !text.endsWith("}")) return throw IllegalArgumentException("Strings are not json object types")
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
        if (!text.startsWith("{") && !text.endsWith("}")) return throw IllegalArgumentException("Strings are not json object types")
        return Gson().fromJson(text, t)
    }

    /**
     * Converts an array instance from the <T> a list string
     * @param text The string of the array type
     * @param t Data custom Javabean
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun <T> getJsonArrayList(text: String, t: Class<T>): List<T> {
        if (!text.startsWith("[") && !text.endsWith("]")) return throw IllegalArgumentException("Strings are not json array types")
        val list: List<T> = Gson().fromJson(
            JSONArray(text).toString(),
            object : TypeToken<List<T?>?>() {}.type
        )
        return list
    }

    /**
     *Get the appropriate object content with the jason string key
     * @param res The string of the object type
     * @param key key
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun getJsonValue(res: String, key: String): Any {
        if (!res.startsWith("{") && !res.endsWith("}")) return throw IllegalArgumentException("Strings are not json object types")
        return JsonParser().parse(res).getAsJsonObject().get(key)
    }

    /**
     * Get the appropriate object content with the jason string key
     * @param jsonObject The string of the object type
     * @param key key
     */
    @Throws(JsonSyntaxException::class)
    @JvmStatic
    fun getAsJsonObjectValue(jsonObject: String, key: String): Any {
        if (jsonObject.contains("\"$key\"", ignoreCase = true)) {
            val parser = JsonParser()
            val element: JsonElement = parser.parse(jsonObject)
            val root = element.asJsonObject
            var keyValue: JsonElement? = null
            try {
                keyValue = root.get(key)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }
            if (keyValue == null) return throw IllegalStateException("The key currently exists but is not in this group")
            return JsonParser().parse(jsonObject).getAsJsonObject().getAsJsonObject().get(key)
        }
        return throw IllegalStateException("This key does not exist at present")
    }
}