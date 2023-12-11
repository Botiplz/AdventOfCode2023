package org.botiplz.util.map

fun <K, V> HashMap<K, V>.removeIf(filter: (index: K, V) -> Boolean) {
    val keysToRemove = this.filter { filter.invoke(it.key, it.value) }.keys
    for (k in keysToRemove) {
        remove(k)
    }
}