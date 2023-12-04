package org.botiplz.days.day04

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.Point
import org.botiplz.util.list.charsAround
import org.botiplz.util.list.indicesAround
import org.botiplz.util.numbers.pow
import org.botiplz.util.stream.multiply

class Day04 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        println(lines.map { line ->
            2.pow(line.split(":").last().split("|")
                .map { group -> group.split(" ").filter(String::isNotEmpty).map(String::toInt) }
                .reduce { a, b -> a.intersect(b).toList() }.size - 1
            )
        }.sum())
    }

    override fun part2(lines: List<String>) {
        val instances = hashMapOf<Int, Int>()
        for (card in lines.indices) {
            instances[card] = 1
        }
        for (card in lines.indices) {
            val line = lines[card]
            val wins = line.split(":").last().split("|")
                .map { group -> group.split(" ").filter(String::isNotEmpty).map(String::toInt) }
                .reduce { a, b -> a.intersect(b).toList() }.size

            for (win in 1..Math.min(wins, lines.lastIndex - card)) {
                instances[card + win] =  instances[card + win]!! + instances[card]!!
            }
        }
        println(instances.values.sum())
    }


}
