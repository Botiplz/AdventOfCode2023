package org.botiplz.days.day03

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.Point
import org.botiplz.util.list.charsAround
import org.botiplz.util.list.indicesAround
import org.botiplz.util.stream.multiply

class Day03 : AbstractDay() {
    override fun test(lines: List<String>) {
        part1(lines)
        println()
    }

    override fun part1(lines: List<String>) {

        val parts = arrayListOf<Int>()
        val pointsInNumber = hashSetOf<Point>()
        val validPoints = hashSetOf<Point>()
        val invalidPoints = hashSetOf<Point>()

        lines.forEachIndexed { y, line ->
            pointsInNumber.clear()
            line.forEachIndexed { x, char ->
                if (char.isDigit()) {
                    pointsInNumber.add(Point(x, y))
                }

                if ((!char.isDigit() || x == line.lastIndex) && pointsInNumber.isNotEmpty()) {
                    var number = 0
                    pointsInNumber.sortedBy { it.x }.forEach {
                        number = number * 10 + lines[it.y][it.x].digitToInt()
                    }
                    if (lines.charsAround(pointsInNumber).any { !it.isDigit() && it != '.' }) {
                        parts.add(number)
                        validPoints.addAll(pointsInNumber)
                    } else {
                        invalidPoints.addAll(pointsInNumber)
                    }

                    pointsInNumber.clear()
                }
            }
        }

        val green = "\u001B[32m"
        val red = "\u001b[31m"
        val reset = "\u001b[0m"

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (validPoints.contains(Point(x, y)))
                    print(green)
                if (invalidPoints.contains(Point(x, y)))
                    print(red)
                print(char)
                print(reset)
            }
            println()
        }
        println(parts.sum())

        println()
    }

    override fun part2(lines: List<String>) {
        val gears = hashMapOf<Point, ArrayList<Int>>()
        val pointsInNumber = hashSetOf<Point>()


        lines.forEachIndexed { y, line ->
            pointsInNumber.clear()
            line.forEachIndexed { x, char ->
                if (char.isDigit()) {
                    pointsInNumber.add(Point(x, y))
                }

                if ((!char.isDigit() || x == line.lastIndex) && pointsInNumber.isNotEmpty()) {
                    var number = 0
                    pointsInNumber.sortedBy { it.x }.forEach {
                        number = number * 10 + lines[it.y][it.x].digitToInt()
                    }

                    lines.indicesAround(pointsInNumber).forEach {
                        if (lines[it.y][it.x] == '*') {
                            gears.putIfAbsent(it, arrayListOf())
                            gears[it]!!.add(number)
                        }
                    }

                    pointsInNumber.clear()
                }
            }
        }

        val green = "\u001B[32m"
        val red = "\u001b[31m"
        val reset = "\u001b[0m"

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                val point = Point(x, y)
                if (gears.contains(point)) {
                    if (gears[point]!!.size == 2)
                        print(green)
                    else
                        print(red)

                }
                print(char)
                print(reset)
            }
            println()
        }
        println(
            gears.filter { it.value.size == 2 }.map { it.value.multiply() }.sum()
        )
    }


}
