package solution

import util.getInputAsText
import java.lang.Exception

private data class Position(val x: Int, val depth: Int, val aim: Int)
private data class MoveCommand(val direction: String, val steps: Int)

private fun level2() {
    val input = getInputAsText(2)
        .lines()
        .map { line ->
            MoveCommand(line.substringBefore(" ").trim(), line.substringAfter(" ").toInt())
        }

    val finalPosition = input.fold(Position(0, 0, 0)) { acc, s ->
        when (s.direction) {
            "forward" -> acc.copy(x = acc.x + s.steps, depth = acc.depth + (acc.aim * s.steps))
            "down" -> acc.copy(aim = acc.aim + s.steps)
            "up" -> acc.copy(aim = acc.aim - s.steps)
            else -> throw Exception("Invalid input ${s.direction}")
        }
    }

    println(finalPosition.x * finalPosition.depth)
}

fun main() {
    level2()
}