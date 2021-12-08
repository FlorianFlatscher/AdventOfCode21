package solution.day7

import util.getInputAsText
import kotlin.math.abs
import kotlin.math.min


private fun solve1() {
    val input = getInputAsText(7).split(",").map { it.toInt() }

    val res = input.minByOrNull { position ->
        input.sumOf { abs(position - it) }
    }!!

    println(input.sumOf { abs(res - it) })

}

private fun solve2() {
    val input = getInputAsText(7).split(",").map { it.toInt() }

    var minV = Int.MAX_VALUE
    List(input.maxOrNull()!!) {i -> i}.forEach { position ->
        minV = min(input.sumOf { other ->
            val distance = abs(position-other)
            var sum = 0
            for (i in 1..distance) {
                sum += i
            }
            sum
        }, minV)
    }
    println(input.sumOf { List(abs(minV - it).toInt()) { i -> i.toLong() + 1 }.sum() })
}


fun main() {
    solve1()
    solve2()
}

