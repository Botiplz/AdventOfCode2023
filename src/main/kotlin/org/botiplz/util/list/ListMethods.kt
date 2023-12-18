package org.botiplz.util.list

import org.botiplz.util.geo.Point

fun List<String>.indicesAround(point: Set<Point>): List<Point> {
    return point.flatMap {
        it.pointsAroundDiagonally()
            .filter(this::containsPoint)
    }
        .filter { !point.contains(it) }
        .distinct()
}

fun List<String>.containsPoint(it: Point) = it.y in 0..lastIndex && it.x in 0..this[it.y].lastIndex

fun List<List<Any>>.containsPointList(it: Point) = it.y in 0..lastIndex && it.x in 0..this[it.y].lastIndex

fun List<String>.charsAround(point: Set<Point>) = indicesAround(point).map { this[it.y][it.x] }


fun List<String>.charAtPoint(it: Point) = this[it.y][it.x]

fun <T> List<List<T>>.elementAtPoint(it: Point) = this[it.y][it.x]


fun List<String>.forEachChar(func: (x: Int, y: Int, line: String, c: Char) -> Unit) {
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            func.invoke(x, y, line, c)
        }
    }
}

fun <T> List<List<T>>.forEachElement(func: (x: Int, y: Int, row: List<T>, element: T) -> Unit) {
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            func.invoke(x, y, line, c)
        }
    }
}

fun List<String>.split(separator: String): List<List<String>> {
    val lists = arrayListOf<List<String>>()

    var currentList = arrayListOf<String>()

    for (line in this) {
        if (line == separator) {
            lists.add(currentList)
            currentList = arrayListOf()
        } else
            currentList.add(line)
    }

    lists.add(currentList)
    return lists
}

fun List<String>.transpose(): List<String> {
    val transposedList = ArrayList<String>()
    first().forEach { _ -> transposedList.add("") }
    forEachIndexed { _, line ->
        line.forEachIndexed { x, c ->
            transposedList[x] = transposedList[x] + c
        }
    }
    return transposedList
}