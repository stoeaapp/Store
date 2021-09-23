package com.imagine.mohamedtaha.store.util

import java.text.SimpleDateFormat
import java.util.*

object TimeAndDate {
    //get date
    fun getDate(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    //get time
    fun getTime(): String? {
        val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val time = Date()
        return dateFormat.format(time)
    }
}