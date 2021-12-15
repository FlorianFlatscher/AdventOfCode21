package solution.day15

import util.Solution
import java.util.*

data class Point(val x: Int, val y: Int)

data class WayPoint(val point: Point, var riskLevel: Int, var fValue: Int, var from: Point?)

fun solve1(): Any? {
    val data = Solution.getInputAsText()

    val cave = mutableMapOf<Point, WayPoint>()

    data.lines().forEachIndexed { y, it ->
        it.forEachIndexed { x, n ->
            cave[Point(x, y)] = (WayPoint(Point(x, y), n.digitToInt(), Int.MAX_VALUE, null))
        }
    }
    cave[Point(0, 0)]!!.fValue = 0
    cave[Point(0, 0)]!!.riskLevel = 0

    val open = ArrayDeque(listOf(Point(0, 0)))
    val closed = mutableSetOf(Point(0, 0))

    while (open.isNotEmpty()) {
        val curr = open.removeFirst()
        val currWp = cave[curr]!!
        for (offset in sequenceOf(listOf(1, 0), listOf(0, 1), listOf(-1, 0), listOf(0, -1))) {
            val xx = offset[0]
            val yy = offset[1]
            if (xx == 0 && yy == 0) continue
            val p = Point(curr.x + xx, curr.y + yy)
            val wp = cave[p] ?: continue
            if (closed.contains(p)) {
                if (currWp.fValue + currWp.riskLevel < wp.fValue) {
                    wp.fValue = currWp.fValue + currWp.riskLevel
                    wp.from = curr
                    if (!open.contains(p)) {
                        open.add(p)
                    }
                }
            } else {
                wp.fValue = currWp.fValue + currWp.riskLevel
                wp.from = curr
                closed.add(p)
                open.add(p)
            }
        }
    }

    val last = cave[Point(cave.maxOf { it.key.x }, cave.maxOf { it.key.y })]!!

    return last.fValue + last.riskLevel
}


fun solve2(): Any? {
    val data = Solution.getInputAsText()

    val cave = mutableMapOf<Point, WayPoint>()

    val height = data.lines().size
    val width = data.lines()[0].length
    data.lines().forEachIndexed { y, it ->
        it.forEachIndexed { x, n ->
            for (xx in 0..4) {
                for (yy in 0..4) {
                    var cost = n.digitToInt() + xx + yy
                    if (cost > 9) {
                        cost -= 9
                    }
                    val p = Point(xx*width + x, yy * height + y)
                    cave[p] = WayPoint(p, cost, Int.MAX_VALUE, null)
                }
            }
        }
    }
    cave[Point(0, 0)]!!.fValue = 0
    cave[Point(0, 0)]!!.riskLevel = 0

    val open = ArrayDeque(listOf(Point(0, 0)))
    val closed = mutableSetOf(Point(0, 0))

    while (open.isNotEmpty()) {
        val curr = open.removeFirst()
        val currWp = cave[curr]!!
        for (offset in sequenceOf(listOf(1, 0), listOf(0, 1), listOf(-1, 0), listOf(0, -1))) {
            val xx = offset[0]
            val yy = offset[1]
            if (xx == 0 && yy == 0) continue
            val p = Point(curr.x + xx, curr.y + yy)
            val wp = cave[p] ?: continue
            if (closed.contains(p)) {
                if (currWp.fValue + currWp.riskLevel < wp.fValue) {
                    wp.fValue = currWp.fValue + currWp.riskLevel
                    wp.from = curr
                    if (!open.contains(p)) {
                        open.add(p)
                    }
                }
            } else {
                wp.fValue = currWp.fValue + currWp.riskLevel
                wp.from = curr
                closed.add(p)
                open.add(p)
            }
        }
    }

    val last = cave[Point(cave.maxOf { it.key.x }, cave.maxOf { it.key.y })]!!

    return last.fValue + last.riskLevel
}

fun main() {
    Solution.run(15, ::solve1)
    Solution.run(15, ::solve2)
}