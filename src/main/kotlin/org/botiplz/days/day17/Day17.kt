package org.botiplz.days.day17

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.Point
import org.botiplz.util.list.*

class Day17 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        navigate(lines, 0, 3)
    }

    override fun part2(lines: List<String>) {
        navigate(lines, 4, 10)
    }


    private fun navigate(lines: List<String>, minSteps: Int, maxSteps: Int) {

        val distances = hashMapOf<NodeTuple, Int>()
        val start1 = NodeTuple(Point.zero(), Point.right(), 0, minSteps, maxSteps)
        val start2 = NodeTuple(Point.zero(), Point.down(), 0, minSteps, maxSteps)
        distances[start1] = 0
        distances[start2] = 0
        val queue = hashMapOf(start1 to 0, start2 to 0)

        while (queue.isNotEmpty()) {
            val entry = queue.minBy { it.value }
            val currentPoint = entry.key
            queue.remove(currentPoint)

            if (currentPoint.printIfEnd(lines, entry.value))
                return

            for (nextPoint in currentPoint.pointsAround(lines)) {
                val next = currentPoint.nextTuple(nextPoint)
                val cost = distances[currentPoint]!! + lines.charAtPoint(nextPoint).digitToInt()
                if (cost < (distances[next] ?: Int.MAX_VALUE)) {
                    distances[next] = cost
                    queue[next] = cost
                }
            }
        }
    }
}


data class NodeTuple(val point: Point, val dir: Point, val amount: Int, val minSteps: Int, val maxSteps: Int) {

    fun printIfEnd(lines: List<String>, heat: Int): Boolean {
        if (point == Point(lines.lastIndex, lines[0].lastIndex)) {
            println(heat)
            return true
        }
        return false
    }

    fun pointsAround(lines: List<String>): List<Point> {
        return point.pointsAround().filter { nextPointValid(it, lines) }
    }

    private fun nextPointValid(nextPoint: Point, lines: List<String>): Boolean {
        if (!lines.containsPoint(nextPoint)) return false
        val newDir = nextPoint - point
        if (newDir == dir.flip()) return false
        if (newDir != dir && amount < minSteps) return false
        if (newDir == dir && amount == maxSteps) return false
        return true
    }

    fun nextTuple(nextPoint: Point): NodeTuple {
        val newDir = nextPoint - point
        return NodeTuple(nextPoint, newDir, if (newDir == dir) amount + 1 else 1, minSteps, maxSteps)
    }
}
