package solution.day9

import util.getInputAsText

data class Point(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

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

fun solve1() {
    var input = getInputAsText(9).lines();
    var chars = input.map {it.toCharArray()}
    var map = chars.map { it.map {it.digitToInt() }}

    var sum = 0;
    for (x in map.indices) {
        for (y in map[x].indices) {
            val level = map[x][y];

            val top = if (y > 0) map[x][y-1] else Int.MAX_VALUE
            val right = if (x < map.size - 1) map[x+1][y] else Int.MAX_VALUE
            val bottom = if (y < map[x].size - 1) map[x][y+1] else Int.MAX_VALUE
            val left = if (x > 0) map[x-1][y] else Int.MAX_VALUE

            if (top > level && right > level && bottom > level && left > level) {
                sum += level + 1
            }
        }
    }

    println(sum)
}

fun solve2 () {
    var input = getInputAsText(9).lines();
    var chars = input.map {it.toCharArray()}
    var map = chars.map { it.map {it.digitToInt() }}


    var basinPoints = mutableListOf<Point>()
    for (x in map.indices) {
        for (y in map[x].indices) {
            val level = map[x][y];

            val top = if (y > 0) map[x][y-1] else Int.MAX_VALUE
            val right = if (x < map.size - 1) map[x+1][y] else Int.MAX_VALUE
            val bottom = if (y < map[x].size - 1) map[x][y+1] else Int.MAX_VALUE
            val left = if (x > 0) map[x-1][y] else Int.MAX_VALUE

            if (top > level && right > level && bottom > level && left > level) {
                basinPoints.add(Point(x, y))
            }
        }
    }

    val basinSizes = mutableListOf<Int>()

    for (p in basinPoints) {
        val closed = hashSetOf<Point>()
        val open = ArrayDeque(listOf(p))
        closed.add(p)

        while (open.size > 0) {
            val next = open.removeFirst()
            val x = next.x
            val y = next.y

            val top = if (y > 0) Point(x, y - 1) else null
            val right = if (x < map.size - 1) Point(x + 1, y) else null
            val bottom = if (y < map[x].size - 1) Point(x, y + 1) else null
            val left = if (x > 0) Point(x - 1, y) else null

            if (top != null && !closed.contains(top) && map[top.x][top.y] < 9) {
                closed.add(top)
                open.addLast(top)
            }
            if (right != null && !closed.contains(right) && map[right.x][right.y] < 9) {
                closed.add(right)
                open.addLast(right)
            }
            if (bottom != null && !closed.contains(bottom) && map[bottom.x][bottom.y] < 9) {
                closed.add(bottom)
                open.addLast(bottom)
            }
            if (left != null && !closed.contains(left) && map[left.x][left.y] < 9) {
                closed.add(left)
                open.addLast(left)
            }
        }
        basinSizes.add(closed.size)
    }
    println(basinSizes.sortedDescending().take(3).reduce {acc, it -> acc * it})
}

fun main () {
    solve1()
    solve2()
}