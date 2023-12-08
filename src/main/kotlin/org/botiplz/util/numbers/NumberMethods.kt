package org.botiplz.util.numbers

import kotlin.math.min
import kotlin.math.pow

fun Int.pow(power: Int): Int {
    return this.toDouble().pow(power).toInt()
}

fun Long.gcd(other: Long): Long {
    var a = this
    var b = other
    while (b > 0) {
        val temp = b
        b = a % b // remainder
        a = temp
    }
    return a
}

fun Long.lcm(other: Long): Long {
    return this * (other / gcd(other))
}