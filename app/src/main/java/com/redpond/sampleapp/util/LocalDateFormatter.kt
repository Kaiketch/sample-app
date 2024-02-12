package com.redpond.sampleapp.util

import java.time.LocalDateTime

fun LocalDateTime.formatToJpDateTime(): String {
    return "${this.dayOfMonth}月${this.dayOfMonth}日 ${if (this.hour.toString().length == 1) 0 else ""}${this.hour}:${if (this.minute.toString().length == 1) 0 else ""}${this.minute}"
}

fun String.parseToYyyyMMddHHmm(): LocalDateTime {
    val regex = """^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}$""".toRegex()
    val isoString = if (regex.matches(this)) {
        this.replaceFirst(" ", "T")
    } else {
        throw IllegalStateException(Exception("Invalid date format"))
    }
    return LocalDateTime.parse(isoString)
}
