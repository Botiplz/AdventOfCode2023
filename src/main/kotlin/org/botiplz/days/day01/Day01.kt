package org.botiplz.days.day01

import org.botiplz.days.AbstractDay
import org.botiplz.util.numbers.firstDigit
import org.botiplz.util.numbers.lastDigit
import org.botiplz.util.string.firstAndLast

class Day01 : AbstractDay() {
    override fun test(lines: List<String>) {

        part2(lines)
    }

    override fun part1(lines: List<String>) {
        println(lines.map { line -> line.filter { it.isDigit() }.firstAndLast().toInt() }.sum())
    }

    override fun part2(lines: List<String>) {
        println(lines.map { it.firstDigit() * 10 + it.lastDigit() }.sum())


    }


}