package com.alien.base.tools

import android.text.TextUtils
import com.google.common.base.Objects
import java.text.DecimalFormat
import java.util.regex.Pattern

object StringUtils{

    fun isMobile(mobiles: String): Boolean {
        val telRegex = "\\d{11}"
        return if (TextUtils.isEmpty(mobiles))
            false
        else
            mobiles.matches(telRegex.toRegex())
    }

    fun mobileEncr(mobiles: String): String? {
        return if (isMobile(mobiles)) {
            mobiles.substring(0, 3) + "****" + mobiles.substring(7, mobiles.length)
        } else null
    }

    fun equals(s1: String, s2: String): Boolean {
        return Objects.equal(s1, s2)
    }

    fun getLength(cs: CharSequence?): Int {
        return cs?.length ?: 0
    }

    fun isEmpty(str: String?): Boolean {
        return str == null || str.length == 0
    }

    //替换空格，tab，制表符，换行符 以及全角空格等
    fun replaceBlank(str: String?): String {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\u3000|\\s*|\t|\r|\n")// \u3000表示全角空格
            val m = p.matcher(str)
            dest = m.replaceAll("")
        }
        return dest
    }

    fun formatValue(value: Int?): String {
        val format = DecimalFormat(",###")
        return format.format(value)
    }
}