package com.alien.base.tools

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getToday(): String{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(Date())
    }
}