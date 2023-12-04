package org.botiplz.util

import org.botiplz.days.AbstractDay
import org.botiplz.days.day01.Day01
import org.botiplz.days.day03.Day03
import org.botiplz.days.day02.Day02
import org.botiplz.days.day04.Day04


fun getDay(day: Int): AbstractDay {
    return when (day) {
        1 -> Day01()
        2 -> Day02()
        3 -> Day03()
        4 -> Day04()
        else -> Day01()
    }
}