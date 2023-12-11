package org.botiplz.days.day06

import org.botiplz.days.AbstractDay
import org.botiplz.util.stream.multiply

class Day06 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        val times = lines.first().split(':').last().trim().split(" ").filter(String::isNotEmpty).map(String::toLong)
        val distances = lines.last().split(':').last().trim().split(" ").filter(String::isNotEmpty).map(String::toLong)
        println(
            times.indices.map { index ->
                val totalTime = times[index]
                val distanceToBeat = distances[index]
                (0..totalTime).filter { calcDistance(it, totalTime) > distanceToBeat }.size
            }.multiply()
        )
    }

    override fun part2(lines: List<String>) {
        val totalTime = lines.first().split(':').last().trim().replace(" ", "").toLong()
        val distanceToBeat = lines.last().split(':').last().trim().replace(" ", "").toLong()
        println((0..totalTime).filter { calcDistance(it, totalTime) > distanceToBeat }.size)
    }

    private fun calcDistance(holdTime: Long, totalTime: Long): Long {
        return holdTime * (totalTime - holdTime)
    }


}