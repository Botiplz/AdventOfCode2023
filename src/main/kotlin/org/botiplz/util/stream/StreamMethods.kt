package org.botiplz.util.stream

fun Iterable<Int>.multiply(): Int {
    var multi: Int = 1
    for (element in this) {
        multi *= element
    }
    return multi
}