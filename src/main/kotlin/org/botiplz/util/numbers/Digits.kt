package org.botiplz.util.numbers

enum class Digits(val value: Int) {
    one(1), two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9);

    fun matchesStringAtPosition(string: String, index: Int): Boolean {
        return string.substring(index).startsWith(name) || string.substring(index).startsWith(value.toString())
    }
}

fun String.firstDigit(): Int {
    for (index in 0..lastIndex) {
        val first = Digits.entries.firstOrNull { it.matchesStringAtPosition(this, index) }
        if (first != null)
            return first.value
    }
    return 0
}

fun String.lastDigit(): Int {
    for (index in lastIndex downTo 0) {
        val first = Digits.entries.firstOrNull { it.matchesStringAtPosition(this, index) }
        if (first != null)
            return first.value
    }
    return 0
}