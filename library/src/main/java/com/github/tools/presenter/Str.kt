package com.github.tools.presenter

import java.util.regex.Matcher
import java.util.regex.Pattern

object Str {

    /**
     * simple encapsulation method
     * @param str string
     * @param pattern the rules
     */
    private fun isPositiveQuery(str: String, pattern: String): Boolean {
        val r: Pattern = Pattern.compile(pattern)
        val m: Matcher = r.matcher(str)
        return m.matches()
    }

    /**
     * determines whether the string conforms to the rule
     * @param str string
     */
    @JvmStatic
    fun isNumber(str: String): Boolean {
        return isPositiveQuery(str, "^[0-9]{1,20}$")
    }

    /**
     * determines whether the string conforms to the rule
     * @param str string
     */
    @JvmStatic
    fun isEmail(str: String): Boolean {
        return isPositiveQuery(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$")
    }

    /**
     * determines whether the string conforms to the rule
     * @param str string
     */
    @JvmStatic
    fun isPhone(str: String): Boolean {
        return isPositiveQuery(
            str,
            "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}\$"
        )
    }

    /**
     * determines whether the string conforms to the rule
     * @param str string
     */
    @JvmStatic
    fun isAccount(str: String): Boolean {
        return isPositiveQuery(str, "^[a-zA-Z][a-zA-Z0-9_]{4,15}\$")
    }

    /**
     * determines whether the string conforms to the rule
     * @param str string
     */
    @JvmStatic
    fun isDomainName(str: String): Boolean {
        return isPositiveQuery(
            str,
            "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?"
        )
    }
}