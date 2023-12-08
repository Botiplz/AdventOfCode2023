package org.botiplz.days.day08

import org.botiplz.days.AbstractDay
import org.botiplz.util.numbers.lcm

class Day08 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {

        val dirs = lines.first()
        val leftMap = hashMapOf<String, String>()
        val rightMap = hashMapOf<String, String>()

        for (line in lines) {
            if (line.contains("=")) {
                val lineSplit = line.split("=")
                val key = lineSplit.first().trim()
                val cleanedUpValues = lineSplit.last().replace('(', ' ').replace(')', ' ').trim()
                val valueSplit = cleanedUpValues.split(',')
                leftMap[key] = valueSplit.first().trim()
                rightMap[key] = valueSplit.last().trim()
            }
        }

        var currValue = "AAA"
        var steps = 0

        while (currValue != "ZZZ") {
            val dir = dirs[steps % dirs.length]
            currValue = when (dir) {
                'L' -> leftMap[currValue]!!
                'R' -> rightMap[currValue]!!
                else -> currValue
            }

            steps += 1
        }
        println(steps)
    }

    override fun part2(lines: List<String>) {
        val dirs = lines.first()
        val leftMap = hashMapOf<String, String>()
        val rightMap = hashMapOf<String, String>()

        for (line in lines) {
            if (line.contains("=")) {
                val lineSplit = line.split("=")
                val key = lineSplit.first().trim()
                val cleanedUpValues = lineSplit.last().replace('(', ' ').replace(')', ' ').trim()
                val valueSplit = cleanedUpValues.split(',')
                leftMap[key] = valueSplit.first().trim()
                rightMap[key] = valueSplit.last().trim()
            }
        }

        val startNodes = leftMap.keys.filter { it.endsWith("A") }

        val firstSolutions = arrayListOf<Long>()

        for (index in startNodes.indices) {

            var currValue = startNodes[index]
            var steps = 0L

            while (!currValue.endsWith("Z")) {
                val dir = dirs[(steps % dirs.length).toInt()]
                currValue = when (dir) {
                    'L' -> leftMap[currValue]!!
                    'R' -> rightMap[currValue]!!
                    else -> currValue
                }

                steps += 1
            }
            firstSolutions.add(steps)
        }

        println(firstSolutions.reduce{a,b->a.lcm(b)})

    }






}