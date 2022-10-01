package com.ritier.springr2dbcsample.util

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConverterUtil {
    companion object {
        fun convertStrToLocalDateTime(time: String): LocalDateTime {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")
            return LocalDateTime.parse(time, formatter)
        }

        fun convertStrToDate(time: String): java.sql.Date {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            val date = formatter.parse(time)
            return Date(date.time)
        }
    }
}