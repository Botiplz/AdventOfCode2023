package org.botiplz.util

import org.botiplz.days.AbstractDay


fun getDay(day: Int): AbstractDay {

    val dayFormatted = if (day < 10) "0$day" else "$day"
    return Class.forName("org.botiplz.days.day$dayFormatted.Day$dayFormatted").kotlin.constructors.first()
        .call() as AbstractDay
}