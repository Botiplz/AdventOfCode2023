package org.botiplz.util.geo

class LongPoint(val x: Long, val y: Long) {

    fun up(): LongPoint {
        return LongPoint(x, y + 1)
    }

    fun down(): LongPoint {
        return LongPoint(x, y - 1)
    }

    fun left(): LongPoint {
        return LongPoint(x - 1, y)
    }

    fun right(): LongPoint {
        return LongPoint(x + 1, y)
    }

    fun pointsAround(): List<LongPoint> {
        return listOf(up(), down(), left(), right())
    }

    operator fun minus(prevPoint: LongPoint): LongPoint {
        return LongPoint(x - prevPoint.x, y - prevPoint.y)
    }

    operator fun plus(prevPoint: LongPoint): LongPoint {
        return LongPoint(x + prevPoint.x, y + prevPoint.y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LongPoint

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
        fun topLeft(vararg sensor: LongPoint): LongPoint {
            return LongPoint(sensor.minOfOrNull { it.x }!!, sensor.minOfOrNull { it.y }!!)
        }

        fun bottomRight(vararg sensor: LongPoint): LongPoint {
            return LongPoint(sensor.maxOfOrNull { it.x }!!, sensor.maxOfOrNull { it.y }!!)
        }

    }

}