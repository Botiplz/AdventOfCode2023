package org.botiplz.util.range

fun LongRange.splitByOther(other: LongRange): List<LongRange> {
    if (!intersectsRange(other))
        return listOf(this)

    val newRanges = arrayListOf<LongRange>()
    val intersectRange = intersectRange(other)

    if (intersectRange.first > first) {
        newRanges.add(first..<intersectRange.first)
    }

    if (intersectRange.last < last) {
        newRanges.add(intersectRange.last + 1..last)
    }

    return newRanges

}

fun LongRange.intersectsRange(other: LongRange): Boolean {
    return first <= other.last && other.first <= last
}

fun LongRange.intersectRange(other: LongRange): LongRange {
    return first.coerceAtLeast(other.first)..last.coerceAtMost(other.last)
}