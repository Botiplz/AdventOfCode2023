package org.botiplz.days.day15

import org.botiplz.days.AbstractDay

class Day15 : AbstractDay() {
    override fun test(lines: List<String>) {
        part1(lines)
        part2(lines)
        println()
    }

    override fun part1(lines: List<String>) {
        val sequence = lines.first().split(',')
        println(sequence.sumOf { it.calculate() })
    }

    override fun part2(lines: List<String>) {
        val sequence = lines.first().split(',')

        //init boxes
        val boxes = arrayListOf<ArrayList<String>>()
        for (i in 1..256) {
            boxes.add(arrayListOf())
        }

        sequence.map { s: String ->
            val label = s.split('-').first().split('=').first()
            val group = label.calculate()
            val box = boxes[group]

            if (s.contains('=')) {
                if (box.any { it.startsWith(label) }) {
                    box.map { if (it.startsWith(label)) s else it }.forEachIndexed { i, value -> box[i] = value }
                } else {
                    box.add(s)
                }
            } else if (s.contains('-')) {
                box.removeIf { it.startsWith(label) }
            } else {
                //no-op
            }
        }

        println(boxes.mapIndexed { boxIndex, lenses ->
            lenses.mapIndexed { lensIndex, lens ->
                val focalLength = lens.split('=').last().toInt()
                (boxIndex + 1) * (lensIndex + 1) * focalLength
            }.sum()
        }.sum())
    }

    private fun String.calculate(): Int {
        var currentValue = 0
        for (c in this) {
            currentValue = c.calculate(currentValue)
        }
        return currentValue
    }

    private fun Char.calculate(currentValue: Int): Int {
        return ((currentValue + this.code) * 17) % 256
    }

}