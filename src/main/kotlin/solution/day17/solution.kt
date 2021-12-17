package solution.day17

import util.InputType
import util.Solution
import kotlin.math.max

data class TargetArea(val xLower: Int, val xUpper: Int, val yLower: Int, val yUpper: Int)
data class PVector(val x: Int, val y: Int) {
    operator fun plus(other: PVector): PVector {
        return PVector(x + other.x, y + other.y)
    }
}

fun solve1(): Any? {
    val input = Solution.getInputAsText()
    val (xLower, xUpper, yLower, yUpper) = Regex("target area: x=([0-9\\-]+)[.]{2}([0-9\\-]+), y=([0-9\\-]+)[.]{2}([0-9\\-]+)").matchEntire(
        input
    )?.destructured!!
    val ta = TargetArea(xLower.toInt(), xUpper.toInt(), yLower.toInt(), yUpper.toInt())

    val possibleMaxes = mutableListOf<Int>()
    for (x in 0..100) {
        for (y in -100..100) {
            val res = checkPrope(ta, PVector(x, y))

            if (res) {
                possibleMaxes.add(getPropePath(ta, PVector(x, y)).maxOf { it.y })
            }
        }
    }

    return possibleMaxes.maxOrNull()
}

fun solve2(): Any? {
    val input = Solution.getInputAsText()
    val (xLower, xUpper, yLower, yUpper) = Regex("target area: x=([0-9\\-]+)[.]{2}([0-9\\-]+), y=([0-9\\-]+)[.]{2}([0-9\\-]+)").matchEntire(
        input
    )?.destructured!!
    val ta = TargetArea(xLower.toInt(), xUpper.toInt(), yLower.toInt(), yUpper.toInt())
//    return 0

    val vels = mutableListOf<PVector>()
    for (x in 0..1000) {
        for (y in -100..100) {
            val res = checkPrope(ta, PVector(x, y))

            if (res) {
                vels.add(PVector(x, y))
            }
        }
    }
    return vels.count()
}

fun drawPrope(ta: TargetArea, vel: PVector) {
    println(getPropePath(ta, vel))
}

fun checkPrope(ta: TargetArea, vel: PVector): Boolean {
    val x = getPropePath(ta, vel).takeLast(2)
    return x.any { last -> last.x >= ta.xLower && last.x <= ta.xUpper && last.y >= ta.yLower && last.y <= ta.yUpper }
}

fun getPropePath(ta: TargetArea, vel: PVector): List<PVector> {
    val positions = mutableListOf(PVector(0, 0))

    var currVel = vel
    while (positions.last().x < ta.xUpper && positions.last().y > ta.yLower) {
        positions.add(positions.last() + currVel)
        currVel = currVel.copy(x = max(0, currVel.x - 1), y = currVel.y - 1)
    }

    return positions
}


fun main() {

    Solution.run(17, ::solve1)
    Solution.run(17, ::solve2)
}