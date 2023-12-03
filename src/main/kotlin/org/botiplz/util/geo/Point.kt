package org.botiplz.util.geo

import java.util.function.Consumer
import kotlin.math.sign


class Point(val x: Int, val y: Int) {

    fun up(): Point {
        return Point(x, y + 1)
    }

    fun upLeft(): Point {
        return Point(x - 1, y + 1)
    }

    fun upRight(): Point {
        return Point(x + 1, y + 1)
    }

    fun down(): Point {
        return Point(x, y - 1)
    }

    fun downLeft(): Point {
        return Point(x - 1, y - 1)
    }

    fun downRight(): Point {
        return Point(x + 1, y - 1)
    }

    fun left(): Point {
        return Point(x - 1, y)
    }

    fun right(): Point {
        return Point(x + 1, y)
    }

    fun pointsAround(): List<Point> {
        return listOf(up(), down(), left(), right());
    }

    fun pointsAroundDiagonally(): List<Point> {
        return listOf(upLeft(), up(), upRight(), downLeft(), down(), downRight(), left(), right());
    }

    operator fun minus(prevPoint: Point): Point {
        return Point(x - prevPoint.x, y - prevPoint.y)
    }

    operator fun plus(prevPoint: Point): Point {
        return Point(x + prevPoint.x, y + prevPoint.y)
    }

    fun manhattanDistance(other: Point): Int {
        val distance = this - other
        return Math.abs(distance.x) + Math.abs(distance.y);
    }

    fun direction(): Point {
        return Point(x.sign, y.sign);
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }


    override fun toString(): String {
        return "Point(x=$x, y=$y)"
    }

    companion object {
        fun topLeft(vararg sensor: Point): Point {
            return Point(sensor.map { it.x }.min(), sensor.map { it.y }.min())
        }

        fun bottomRight(vararg sensor: Point): Point {
            return Point(sensor.map { it.x }.max(), sensor.map { it.y }.max())
        }

        fun forPointsInRange(point1: Point, point2: Point, consumer: Consumer<Point>) {
            var analysePoint = point1
            while (analysePoint != point2) {
                consumer.accept(analysePoint)
                analysePoint = analysePoint + (point2 - analysePoint).direction()
            }
            consumer.accept(analysePoint)
        }
    }

}