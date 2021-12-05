package solution

import util.getInputAsText
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.max
import kotlin.math.roundToInt

private data class Pos(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pos

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

private fun solve1() {
    var input = getInputAsText(5);
    var grid = hashMapOf<Pos, Int>();

    input.lines().forEach { line ->
        val lr = line.split(" -> ")
        val l = lr[0].split(",")
        val r = lr[1].split(",")
        val from = Pos(l[0].toInt(), l[1].toInt())
        val to = Pos(r[0].toInt(), r[1].toInt())

        if (!(from.x == to.x || from.y == to.y)) {
            return@forEach
        }
        val deltaX = to.x - from.x
        val deltaY = to.y - from.y
        val m = max(abs(deltaX), abs(deltaY))
        for (d in 0..m) {
            val x = d/m.toDouble() * deltaX + from.x
            val y = d/m.toDouble() * deltaY + from.y
            grid[Pos(x.roundToInt(), y.roundToInt())] = grid.getOrDefault(Pos(x.roundToInt(), y.roundToInt()), 0) + 1;
        }
    }
    println(grid.count { entry -> entry.value >= 2 })
}

private fun solve2() {
    var input = getInputAsText(5);
    var grid = hashMapOf<Pos, Int>();

    input.lines().forEach { line ->
        val lr = line.split(" -> ")
        val l = lr[0].split(",")
        val r = lr[1].split(",")
        val from = Pos(l[0].toInt(), l[1].toInt())
        val to = Pos(r[0].toInt(), r[1].toInt())

        val deltaX = to.x - from.x
        val deltaY = to.y - from.y
        val m = max(abs(deltaX), abs(deltaY))
        for (d in 0..m) {
            val x = d/m.toDouble() * deltaX + from.x
            val y = d/m.toDouble() * deltaY + from.y
            grid[Pos(x.roundToInt(), y.roundToInt())] = grid.getOrDefault(Pos(x.roundToInt(), y.roundToInt()), 0) + 1;
        }
    }
    println(grid)
    println(grid.count { entry -> entry.value >= 2 })
}

fun main() {
    solve1();
    solve2();
}