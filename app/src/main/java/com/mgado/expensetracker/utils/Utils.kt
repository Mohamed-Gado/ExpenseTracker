package com.mgado.expensetracker.utils
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getDateTimeFormatted(s: Long?): String? {
    try {
        val sdf = DateFormat.getDateTimeInstance()
        return sdf.format(s)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun formatDate(date: LocalDate, showMonth: Boolean): String {
    val formatter = DateTimeFormatter.ofPattern(if (showMonth) "MMM yyyy" else "yyyy", Locale.getDefault())
    return date.format(formatter)
}

fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin