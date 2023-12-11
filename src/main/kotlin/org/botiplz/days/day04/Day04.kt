package org.botiplz.days.day04

import org.botiplz.days.AbstractDay
import org.botiplz.util.numbers.pow

class Day04 : AbstractDay() {

    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        println(lines.sumOf { line ->
            2.pow(line.split(":").last().split("|")
                .map { group -> group.split(" ").filter(String::isNotEmpty).map(String::toInt) }
                .reduce { a, b -> a.intersect(b.toSet()).toList() }.size - 1
            )
        })
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
                .reduce { a, b -> a.intersect(b.toSet()).toList() }.size

            for (win in 1..wins.coerceAtMost(lines.lastIndex - card)) {
                instances[card + win] = instances[card + win]!! + instances[card]!!
            }
        }
        println(instances.values.sum())
    }

}
