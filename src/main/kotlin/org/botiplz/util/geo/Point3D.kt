package org.botiplz.util.geo

data class Point3D(val x: Int, val y: Int, val z: Int) {


    fun neighbors(): List<Point3D> {
        return listOf(
            Point3D(x + 1, y, z), Point3D(x - 1, y, z), Point3D(x, y + 1, z), Point3D(x, y - 1, z),
            Point3D(x, y, z + 1), Point3D(x, y, z - 1)
        )
    }

    fun outOfRange(xRange: IntRange, yRange: IntRange, zRange: IntRange): Boolean {
        return x !in xRange || y !in yRange || z !in zRange
    }


}