package org.botiplz.days.day13

import org.botiplz.days.AbstractDay
import org.botiplz.util.list.split
import org.botiplz.util.list.transpose

class Day13 : AbstractDay() {
    override fun test(lines: List<String>) {
        calc(lines, 1)
    }

    override fun part1(lines: List<String>) {
        calc(lines, 0)
    }

    override fun part2(lines: List<String>) {
        calc(lines, 1)
    }

    private fun calc(lines: List<String>, totalDiffsAllowed: Int) {
        val maps = lines.split("")
        println(
            maps.sumOf { map ->
                val transpose = map.transpose()
                val rowMirror = map.getRowMirror(totalDiffsAllowed)
                val colMirror = transpose.getRowMirror(totalDiffsAllowed)
                100 * rowMirror + colMirror
            }
        )
    }


    private fun List<String>.getRowMirror(totalDiffsAllowed: Int): Int {
        for (row in 1..lastIndex) {
            val checkRow = row.coerceAtMost(1 + lastIndex - row)
            var diffs = 0
            for (offset in 1..checkRow) {
                val lower = row - offset
                val upper = row + offset - 1
                diffs += this[lower].differences(this[upper])
            }
            if (diffs == totalDiffsAllowed) {
                return row
            }
        }

        return 0
    }

    private fun String.differences(other: String): Int {
        return this.filterIndexed { index, c -> c != other[index] }.length
    }

}