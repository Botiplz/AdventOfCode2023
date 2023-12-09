package org.botiplz.util.list

import org.botiplz.util.geo.Point

fun List<String>.indicesAround(point: Set<Point>): List<Point> {
    return point.flatMap {
        it.pointsAroundDiagonally().filter { it.y in 0..lastIndex && it.x in 0..this[it.y].lastIndex }
    }.filter { !point.contains(it) }.distinct()
}

fun List<String>.charsAround(point: Set<Point>): List<Char> {
    return indicesAround(point).map { this[it.y][it.x] }
}

