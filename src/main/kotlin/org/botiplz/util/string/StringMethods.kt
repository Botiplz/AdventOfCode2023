package org.botiplz.util.string

fun String.firstAndLast(): String {
    val thisString = this
    return buildString {
        append(thisString.first())
        append(thisString.last())
    }
}