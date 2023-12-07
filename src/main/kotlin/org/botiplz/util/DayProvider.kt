package org.botiplz.util

import org.botiplz.days.AbstractDay
import org.botiplz.days.day01.Day01
import org.botiplz.days.day06.Day06
import org.botiplz.days.day03.Day03
import org.botiplz.days.day02.Day02
import org.botiplz.days.day04.Day04
import org.botiplz.days.day05.Day05
import org.botiplz.days.day07.Day07


fun getDay(day: Int): AbstractDay {
    return when (day) {
        1 -> Day01()
        2 -> Day02()
        3 -> Day03()
        4 -> Day04()
        5 -> Day05()
        6 -> Day06()
        7 -> Day07()
        else -> Day01()
    }
}