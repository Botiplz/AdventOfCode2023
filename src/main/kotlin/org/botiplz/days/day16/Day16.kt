package org.botiplz.days.day16

import org.botiplz.days.AbstractDay
import org.botiplz.util.geo.Point
import org.botiplz.util.list.charAtPoint
import org.botiplz.util.list.containsPoint

class Day16 : AbstractDay() {
    override fun test(lines: List<String>) {
        part1(lines)
    }

    override fun part1(lines: List<String>) {
        val cache = hashMapOf<Point, HashSet<Char>>()
        analyze(lines, Point(-1, 0), '>', cache, true)
        println(cache.keys.size)
    }

    override fun part2(lines: List<String>) {
        val startingPositions = hashMapOf<Point, Char>()
        for (x in 0..lines[0].lastIndex) {
            val startPointDown = Point(x, -1)
            startingPositions[startPointDown] = 'v'

            val startPointUp = Point(x, lines.size)
            startingPositions[startPointUp] = '^'
        }

        for (y in 0..lines.lastIndex) {
            val startPointRight = Point(-1, y)
            startingPositions[startPointRight] = '>'

            val startPointLeft = Point(-1, y)
            startingPositions[startPointLeft] = '<'

        }

        println(
            startingPositions.map {
                val cache = hashMapOf<Point, HashSet<Char>>()
                analyze(lines, it.key, it.value, cache, true)
                cache.keys.size
            }.max()
        )

    }
}

private fun analyze(
    lines: List<String>,
    point: Point,
    char: Char,
    cache: HashMap<Point, HashSet<Char>>,
    start: Boolean = false,
) {

    if (!start) {
        if (!lines.containsPoint(point)) return
        cache.putIfAbsent(point, hashSetOf())
        if (cache[point]!!.contains(char)) return
        cache[point]!!.add(char)
    }



    if (char == '>') {
        val nextPoint = point.right()
        if (lines.containsPoint(nextPoint)) {
            val nextChar = lines.charAtPoint(nextPoint)
            when (nextChar) {
                '.' -> {
                    analyze(lines, nextPoint, '>', cache, false)
                }

                '|' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('|'))
                    analyze(lines, nextPoint, '^', cache)
                    analyze(lines, nextPoint, 'v', cache)
                }

                '-' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('-'))
                    analyze(lines, nextPoint, '>', cache)
                }

                '/' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('/'))
                    analyze(lines, nextPoint, '^', cache)
                }

                '\\' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('\\'))
                    analyze(lines, nextPoint, 'v', cache)
                }
            }

        }
    }


    if (char == '<') {
        val nextPoint = point.left()
        if (lines.containsPoint(nextPoint)) {
            val nextChar = lines.charAtPoint(nextPoint)
            when (nextChar) {
                '.' -> {
                    analyze(lines, nextPoint, '<', cache)
                }

                '|' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('|'))
                    analyze(lines, nextPoint, '^', cache)
                    analyze(lines, nextPoint, 'v', cache)
                }

                '-' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('-'))
                    analyze(lines, nextPoint, '<', cache)
                }

                '/' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('/'))
                    analyze(lines, nextPoint, 'v', cache)
                }

                '\\' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('\\'))
                    analyze(lines, nextPoint, '^', cache)
                }
            }

        }
    }


    if (char == 'v') {
        val nextPoint = point.down()
        if (lines.containsPoint(nextPoint)) {
            val nextChar = lines.charAtPoint(nextPoint)
            when (nextChar) {
                '.' -> {
                    analyze(lines, nextPoint, 'v', cache)
                }

                '|' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('|'))
                    analyze(lines, nextPoint, 'v', cache)
                }

                '-' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('-'))
                    analyze(lines, nextPoint, '<', cache)
                    analyze(lines, nextPoint, '>', cache)
                }

                '/' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('/'))
                    analyze(lines, nextPoint, '<', cache)
                }

                '\\' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('\\'))
                    analyze(lines, nextPoint, '>', cache)
                }
            }
        }
    }

    if (char == '^') {
        val nextPoint = point.up()
        if (lines.containsPoint(nextPoint)) {
            val nextChar = lines.charAtPoint(nextPoint)
            when (nextChar) {
                '.' -> {
                    analyze(lines, nextPoint, '^', cache)
                }

                '|' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('|'))
                    analyze(lines, nextPoint, '^', cache)
                }

                '-' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('-'))
                    analyze(lines, nextPoint, '<', cache)
                    analyze(lines, nextPoint, '>', cache)
                }

                '/' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('/'))
                    analyze(lines, nextPoint, '>', cache)
                }

                '\\' -> {
                    cache.putIfAbsent(nextPoint, hashSetOf('\\'))
                    analyze(lines, nextPoint, '<', cache)
                }
            }

        }
    }


}


