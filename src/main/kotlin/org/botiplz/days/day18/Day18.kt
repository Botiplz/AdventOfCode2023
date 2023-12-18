package org.botiplz.days.day18

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.LongPoint

class Day18 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        val map = Map(lines)
        //Pick A = I + (R/2)-1  -> I = A+1-R/2
        val innerPoints = map.area - (map.pointAmount / 2) + 1
        println(innerPoints + map.pointAmount)
    }

    override fun part2(lines: List<String>) {
        val map = Map(lines, 2)
        //Pick A = I + (R/2)-1  -> I = A+1-R/2
        val innerPoints = map.area - (map.pointAmount / 2) + 1
        println(innerPoints + map.pointAmount)
    }

}

@OptIn(ExperimentalStdlibApi::class)
class Map(lines: List<String>, part: Int = 1) {

    val map = arrayListOf<LongPoint>()
    val pointAmount: Long
    val area: Long

    init {
        var currentPoint = LongPoint(0, 0)
        var pointCount = 0L
        for (line in lines) {
            var dir = line.split(' ').first().first()
            var amount = line.split(' ')[1].toLong()

            if (part == 2) {
                val hex = line.split(' ').last().drop(2).dropLast(1)
                dir = when (hex.last().digitToInt()) {
                    0 -> 'R'
                    1 -> 'D'
                    2 -> 'L'
                    3 -> 'U'
                    else -> 'F'
                }
                amount = hex.dropLast(1).hexToLong()
            }

            currentPoint = when (dir) {
                'R' -> currentPoint + LongPoint.right() * amount
                'L' -> currentPoint + LongPoint.left() * amount
                'U' -> currentPoint + LongPoint.up() * amount
                'D' -> currentPoint + LongPoint.down() * amount
                else -> currentPoint
            }
            map.add(currentPoint)
            pointCount += amount
        }
        pointAmount = pointCount

        var areaC = 0L
        for (i in 0..<map.lastIndex) {
            val p1 = map[i]
            val p2 = map[i + 1]
            areaC += (p1.x * p2.y - p2.x * p1.y)
        }
        area = areaC / 2L
    }


}