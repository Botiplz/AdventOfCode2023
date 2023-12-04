package org.botiplz.util.numbers

import kotlin.math.pow

fun Int.pow(power:Int):Int{
    return this.toDouble().pow(power).toInt()
}