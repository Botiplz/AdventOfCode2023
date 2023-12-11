package org.botiplz.days.day10

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.Point
import org.botiplz.util.list.charAtPoint
import org.botiplz.util.list.containsPoint

class Day10 : AbstractDay() {

    private val south = "|7FS"
    private val north = "|LJS"
    private val west = "-7JS"
    private val east = "-LFS"


    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        val connectedPoints = hashMapOf<Point, HashSet<Point>>()
        val startPoint = parse(lines, connectedPoints)
        val distances = distances(startPoint, connectedPoints)
        println(distances.values.filter { it != Int.MAX_VALUE }.max())
    }

    private fun distances(startPoint: Point, connectedPoints: HashMap<Point, HashSet<Point>>): HashMap<Point, Int> {
        val pointsToCheck = hashSetOf(startPoint)

        val distances = hashMapOf<Point, Int>()
        for (key in connectedPoints.keys) {
            distances[key] = Int.MAX_VALUE
        }
        distances[startPoint] = 0

        while (pointsToCheck.isNotEmpty()) {
            val minPoint = pointsToCheck.minBy { distances[it]!! }
            pointsToCheck.remove(minPoint)
            val nextDistance = distances[minPoint]!! + 1
            connectedPoints[minPoint]!!.filter { distances[it]!! > nextDistance }.forEach {
                distances[it] = nextDistance
                pointsToCheck.add(it)
            }
        }
        return distances
    }

    override fun part2(lines: List<String>) {
        val connectedPoints = hashMapOf<Point, HashSet<Point>>()
        val startPoint = parse(lines, connectedPoints)

        val markedPoints = hashSetOf<Point>()
        val distances = distances(startPoint, connectedPoints)

        connectedPoints.filter { distances[it.key]!! == Int.MAX_VALUE }.forEach { empty ->
            var parsedUp = false
            var parsedDown = false
            var crossings = 0
            val emptyPoint = empty.key
            var currentPoint = emptyPoint
            while (currentPoint.x >= 0) {
                val inMainLoop = distances[currentPoint]!! != Int.MAX_VALUE
                if (connectedPoints[currentPoint]!!.contains(currentPoint.up())) {
                    parsedUp = !parsedUp
                }
                if (connectedPoints[currentPoint]!!.contains(currentPoint.down())) {
                    parsedDown = !parsedDown
                }
                if (connectedPoints[currentPoint]!!.isEmpty() || !inMainLoop) {
                    parsedUp = false
                    parsedDown = false
                }
                if (parsedDown && parsedUp) {
                    crossings += 1
                    parsedUp = false
                    parsedDown = false
                }
                currentPoint = currentPoint.left()
            }
            if (crossings % 2 == 1) {
                markedPoints.add(emptyPoint)
            }
        }
        val newLines = ArrayList(lines)
        for (markedPoint in markedPoints) {
            newLines[markedPoint.y] = newLines[markedPoint.y].replaceRange(markedPoint.x, markedPoint.x + 1, "1")
        }
        newLines.forEach { println(it) }
        println(markedPoints.size)
    }


    private fun parse(lines: List<String>, connectedPoints: HashMap<Point, HashSet<Point>>): Point {

        var startPoint = Point(0, 0)

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->

                val point = Point(x, y)
                if (c == 'S') {
                    startPoint = point
                }
                val connectedPointSet = hashSetOf<Point>()
                val up = point.up()
                val down = point.down()
                val left = point.left()
                val right = point.right()
                if (lines.containsPoint(up) && south.contains(lines.charAtPoint(up)) && north.contains(c)) {
                    connectedPointSet.add(up)
                }
                if (lines.containsPoint(down) && north.contains(lines.charAtPoint(down)) && south.contains(c)) {
                    connectedPointSet.add(down)
                }
                if (lines.containsPoint(left) && east.contains(lines.charAtPoint(left)) && west.contains(c)) {
                    connectedPointSet.add(left)
                }
                if (lines.containsPoint(right) && west.contains(lines.charAtPoint(right)) && east.contains(c)) {
                    connectedPointSet.add(right)
                }
                connectedPoints[point] = connectedPointSet
            }
        }

        connectedPoints.filter { entry -> entry.value.size == 1 }.forEach {
            it.value.add(startPoint)
        }
        return startPoint
    }

}