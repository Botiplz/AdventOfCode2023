package org.botiplz.days.day14

import org.botiplz.days.AbstractDay

class Day14 : AbstractDay() {
    override fun test(lines: List<String>) {
        part1(lines)
        part2(lines)
        println()
    }

    override fun part1(lines: List<String>) {
        val map = ArrayList(lines.map { line -> ArrayList(line.map { it }.toList()) })
        roll(map) { lineIndex, charIndex -> north(lineIndex, map, charIndex) }
        println(
            map.mapIndexed { index, chars -> (map.size - index) * chars.filter { it == 'O' }.size }.sum()
        )
    }

    override fun part2(lines: List<String>) {
        val map = ArrayList(lines.map { line -> ArrayList(line.map { it }.toList()) })
        val cycles = 1000000000

        val cycleCache = hashMapOf<Int, Int>()
        var currentCycle = 1
        var foundCycle = false

        while (currentCycle <= cycles) {
            roll(map) { lineIndex, charIndex -> north(lineIndex, map, charIndex) }
            roll(map) { lineIndex, charIndex -> west(lineIndex, map, charIndex) }
            roll(map, yDir = -1) { lineIndex, charIndex -> south(lineIndex, map, charIndex) }
            roll(map, xDir = -1) { lineIndex, charIndex -> east(lineIndex, map, charIndex) }

            cycleCache.entries.filter { mutableEntry -> mutableEntry.value == map.hashCode() }.forEach {
                if (!foundCycle) {
                    println("Cycle: " + currentCycle + " from " + it.key)
                    foundCycle = true
                    val cycleLength = currentCycle - it.key
                    val cyclesToSkip = (cycles - currentCycle - it.key) / cycleLength
                    currentCycle += cyclesToSkip * cycleLength
                }

            }

            cycleCache[currentCycle] = map.hashCode()
            currentCycle += 1

        }

        println(
            map.mapIndexed { index, chars -> (map.size - index) * chars.filter { it == 'O' }.size }.sum()
        )
    }

    private fun roll(
        map: ArrayList<ArrayList<Char>>,
        xDir: Int = 1,
        yDir: Int = 1,
        func: (lineIndex: Int, charIndex: Int) -> Unit
    ) {
        var yIndices = map.indices as IntProgression
        if (yDir < 0) yIndices = yIndices.last downTo yIndices.first

        for (lineIndex in yIndices) {
            val chars = map[lineIndex]
            var xIndices = chars.indices as IntProgression
            if (xDir < 0)
                xIndices = xIndices.reversed()

            for (charIndex in xIndices) {
                val c = chars[charIndex]
                if (c == 'O') {
                    func.invoke(lineIndex, charIndex)
                }
            }
        }
    }

    private fun north(lineIndex: Int, map: ArrayList<ArrayList<Char>>, charIndex: Int) {
        var northIndex = lineIndex - 1
        while (northIndex >= 0 && map[northIndex][charIndex] == '.') {
            map[northIndex][charIndex] = 'O'
            map[northIndex + 1][charIndex] = '.'
            northIndex -= 1
        }
    }

    private fun south(lineIndex: Int, map: ArrayList<ArrayList<Char>>, charIndex: Int) {
        var southIndex = lineIndex + 1
        while (southIndex <= map.lastIndex && map[southIndex][charIndex] == '.') {
            map[southIndex][charIndex] = 'O'
            map[southIndex - 1][charIndex] = '.'
            southIndex += 1
        }
    }

    private fun west(lineIndex: Int, map: ArrayList<ArrayList<Char>>, charIndex: Int) {
        var westIndex = charIndex - 1
        while (westIndex >= 0 && map[lineIndex][westIndex] == '.') {
            map[lineIndex][westIndex] = 'O'
            map[lineIndex][westIndex + 1] = '.'
            westIndex -= 1
        }
    }


    private fun east(lineIndex: Int, map: ArrayList<ArrayList<Char>>, charIndex: Int) {
        val chars = map[lineIndex]
        var eastIndex = charIndex + 1
        while (eastIndex <= chars.lastIndex && map[lineIndex][eastIndex] == '.') {
            map[lineIndex][eastIndex] = 'O'
            map[lineIndex][eastIndex - 1] = '.'
            eastIndex += 1
        }
    }

}