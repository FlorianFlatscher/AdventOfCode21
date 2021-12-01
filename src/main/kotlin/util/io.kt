package util

import java.net.URL

fun getResourceAsText(path: String): String {
    return object {}.javaClass.classLoader.getResource(path)!!.readText()
}

fun getInputAsText(day: Int): String {
    return getResourceAsText("./inputs/day${day}.txt").trim()
}

fun getInputAsIntArray(day: Int): List<Int> {
    return getInputAsText(day)
        .lines()
        .map { it.toInt() }
}