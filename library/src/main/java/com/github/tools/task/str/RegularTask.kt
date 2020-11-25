package com.github.tools.task.str

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

object RegularTask {

    /**
     * simple encapsulation method
     * @param str string
     * @param pattern the rules
     */
    fun isPositiveQuery(str: String, pattern: String): Boolean {
        val r: Pattern = Pattern.compile(pattern)
        val m: Matcher = r.matcher(str)
        return m.matches()
    }

    /**
     * get the url link of the current string
     */
    fun getUrl(str: String):String?{
        val matcher: Matcher = Patterns.WEB_URL.matcher(str)
        if (matcher.find()) return matcher.group()
        return null
    }

}