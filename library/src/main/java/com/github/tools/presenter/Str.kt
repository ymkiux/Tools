package com.github.tools.presenter

import com.github.tools.task.str.RegularTask

object Str {

    /**
     * determines whether the string conforms to the number rule
     * @param str string
     * @return compliance with the rules
     */
    fun isNumber(str: String): Boolean {
        return RegularTask.isPositiveQuery(str, "^[0-9]{1,20}$")
    }

    /**
     * determines whether the string conforms to the email rule
     * @param str string
     * @return compliance with the rules
     */
    fun isEmail(str: String): Boolean {
        return RegularTask.isPositiveQuery(
            str,
            "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"
        )
    }

    /**
     * determines whether the string conforms to the phone rule
     * @param str string
     * @return compliance with the rules
     */
    fun isPhone(str: String): Boolean {
        return RegularTask.isPositiveQuery(
            str,
            "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}\$"
        )
    }

    /**
     * whether the account number is legal (beginning with a letter, allowing 5-16 bytes, allowing alphanumeric underscores)
     * @param str string
     * @return compliance with the rules
     */
    fun isAccount(str: String): Boolean {
        return RegularTask.isPositiveQuery(str, "^[a-zA-Z][a-zA-Z0-9_]{4,15}\$")
    }

    /**
     * determines whether the string conforms to the domain name rule
     * @param str string
     * @return compliance with the rules
     */
    fun isDomainName(str: String): Boolean {
        return RegularTask.isPositiveQuery(
            str,
            "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?"
        )
    }

    /**
     * determine whether the string complies with the url rules
     * @param str string
     * @return compliance with the rules
     */
    fun isUrl(str: String): Boolean {
        return RegularTask.isPositiveQuery(
            str,
            "^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?\$"
        )
    }

}