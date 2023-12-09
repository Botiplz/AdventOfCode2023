package org.botiplz.days.day09

import org.botiplz.days.AbstractDay

class Day09 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        println(
            lines.sumOf { line ->
                line.split(" ").map { it.toInt() }.deltasTillZero().sumOf { it.last() }
            }
        )
    }

    override fun part2(lines: List<String>) {
        println(
            lines.sumOf { line ->
                line.split(" ").map { it.toInt() }.deltasTillZero(true).sumOf { it.first() }
            }
        )
    }

    private fun List<Int>.deltasTillZero(negate: Boolean = false): List<List<Int>> {
        val deltasTillZero = arrayListOf(this)
        var currentList = this
        while (!currentList.all { it == 0 }) {
            currentList = currentList.deltas().map { if (negate) -it else it }
            deltasTillZero.add(currentList)
        }
        return deltasTillZero
    }

    private fun List<Int>.deltas(): List<Int> {
        return this.indices.filter { it != this.lastIndex }.map { this[it + 1] - this[it] }
    }

}