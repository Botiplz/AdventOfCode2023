package org.botiplz.days.day11

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.Point
import org.botiplz.util.list.forEachChar
import kotlin.math.max
import kotlin.math.min

class Day11 : AbstractDay() {
    override fun test(lines: List<String>) {
        part1(lines)
    }

    override fun part1(lines: List<String>) {
        extracted(lines, 2)
    }

    override fun part2(lines: List<String>) {
        extracted(lines, 1000000)
    }

    private fun extracted(lines: List<String>, expandFactor: Long) {
        val points = arrayListOf<Point>()

        lines.forEachChar { x, y, _, c ->
            if (c != '.') {
                points.add(Point(x, y))
            }
        }

        val xRange = 0..points.maxOfOrNull(Point::x)!!
        val yRange = 0..points.maxOfOrNull(Point::y)!!

        points.sortBy { it.y * yRange.max() + it.x }


        val xNoPoint = xRange.filter { x -> points.none { it.x == x } }
        val yNoPoint = yRange.filter { y -> points.none { it.y == y } }
        val pairs =
            points.indices.flatMap { i1 ->
                points.indices.filter { i2 -> i1 != i2 }.map { i2 -> Point(min(i1, i2), max(i1, i2)) }
                    .toSet()
            }.toSet()


        println(
            pairs.sumOf { pair ->
                val point1 = points[pair.x]
                val point2 = points[pair.y]

                val pointRangeX = min(point1.x, point2.x)..max(point1.x, point2.x)
                val pointRangeY = min(point1.y, point2.y)..max(point1.y, point2.y)

                val sumEmpty =
                    xNoPoint.filter { pointRangeX.contains(it) }.size + yNoPoint.filter { pointRangeY.contains(it) }.size
                val expandedEmpty = sumEmpty * (expandFactor - 1)

                point1.manhattanDistance(point2).toLong() + expandedEmpty
            }
        )
    }


}