package org.botiplz.util.stream

fun Iterable<Int>.multiply(): Int {
    var multi = 1
    for (element in this) {
        multi *= element
    }
    return multi
}

fun Iterable<Long>.multiply(): Long {
    var multi = 1L
    for (element in this) {
        multi *= element
    }
    return multi
}