package com.github.tools


import com.google.gson.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap


object Json {

    /**
     * Convert it into a Javabean type with a json string
     * @param text The string of the object type
     * @param cls Data custom Javabean
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun <T> getJsonObject(text: String, cls: Class<T>): T {
        if (!text.startsWith("{") && !text.endsWith("}")) return throw IllegalArgumentException("Strings are not json object types")
        return Gson().fromJson(text, cls)
    }

    /**
     * Converts an array instance from the <T> a list string
     * @param text The string of the array type
     * @param cls Data custom Javabean
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun <T> getJsonArrayList(
        text: String?,
        cls: Class<T>?
    ): List<T>? {
        val list: MutableList<T> = ArrayList()
        try {
            val jsonArray: JsonArray = JsonParser().parse(text).asJsonArray
            for (jsonElement in jsonArray) {
                list.add(Gson().fromJson(jsonElement, cls))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    /**
     *Get the appropriate object content with the json string key
     * @param res The string of the object type
     * @param key key
     */
    @Throws(JSONException::class)
    @JvmStatic
    fun getJsonObjectValue(res: String, key: String): Any {
        if (!res.startsWith("{") && !res.endsWith("}")) return throw IllegalArgumentException("Strings are not json object types")
        return JsonParser().parse(res).getAsJsonObject().get(key)
    }

    /**
     * Get the appropriate array content with the json string key
     * @param jsonArray The string of the array type
     * @param key key
     */
    @Throws(JsonSyntaxException::class)
    @JvmStatic
    fun getAsJsonArrayValue(jsonArray: String, key: String): Any {
        if (jsonArray.contains("\"$key\"", ignoreCase = true)) {
            val parser = JsonParser()
            val element: JsonElement = parser.parse(jsonArray)
            val root = element.asJsonObject
            var keyValue: JsonElement? = null
            try {
                keyValue = root.get(key)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }
            if (keyValue == null) return throw IllegalStateException("The key currently exists but is not in this group")
            return JsonParser().parse(jsonArray).getAsJsonObject().getAsJsonObject().get(key)
        }
        return throw IllegalStateException("This key does not exist at present")
    }

    /**
     *Convert hashMap to JsonObject
     *@param hashMap hashMap param
     */
    @JvmStatic
    fun getJsonObject(hashMap: HashMap<String, String>): JsonObject? {
        val jsonObject = JsonObject()
        for (key in hashMap.keys) {
            if (hashMap[key] != null) jsonObject.add(key, JsonParser().parse(hashMap[key]!!))
        }
        return jsonObject
    }

    /**
     * Convert hashMap to JsonArray
     * @param hashMap hashMap param
     */
    fun getJsonArray(hashMap: HashMap<String, String>): JsonArray? {
        val jsonObject = getJsonObject(hashMap)
        return JsonParser().parse("[$jsonObject]").asJsonArray
    }
}