package org.botiplz.util

import org.botiplz.days.AbstractDay
import org.botiplz.days.day01.Day01


fun getDay(day: Int): AbstractDay {
    return when (day) {
        1 -> Day01()
        else -> Day01()
    }
}