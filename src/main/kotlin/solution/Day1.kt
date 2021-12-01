package solution

import util.getInputAsIntArray
import util.getInputAsText
import java.lang.reflect.Proxy

fun level1(): Int {
    val input = getInputAsIntArray(1)

    val res = input
        .zipWithNext { a, b -> a < b }
        .count { it }

    return res
}

fun level2(): Int {
    val input = getInputAsIntArray(1)

    val windows = mutableMapOf<Int, MutableList<Int>>()

    input
        .forEachIndexed { index, value ->
            val windowIds = listOf(index, index - 1, index - 2)
            windowIds.forEach { windowId ->
                if (windowId < 0) return@forEach
                windows[windowId] = windows[windowId].orEmpty().toMutableList();
                windows[windowId]?.add(value)
            }
        }

    val normalizedMeasurements = windows
        .map { entry ->
            entry.value.sum()
        }

    return normalizedMeasurements.zipWithNext { a, b -> a < b }.count { it }
}


fun main() {
    println(level1());
    println(level2());
}