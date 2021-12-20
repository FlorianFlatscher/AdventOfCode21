package solution.day20

import util.InputType
import util.Solution

data class Point(val x: Int, val y: Int)

fun solve1(): Any? {
    val input = Solution.getInputAsText()
    val split = input.split(Regex("\\r?\\n\\r?\\n"))
    val enhancement = split[0]

    var grid = mutableMapOf<Point, Boolean>()
    for (y in split[1].lines().indices) {
        for ((x, c) in split[1].lines()[y].toList().withIndex()) {
            when (c) {
                '#' -> grid[Point(x, y)] = true
                '.' -> grid[Point(x, y)] = false
                else -> throw IllegalStateException()
            }
        }
    }

    var height = split[1].lines().count()
    var width = split[1].lines()[0].length
    var xx = 0
    var yy = 0

    for (i in 0 until 2) {
        val newGrid = mutableMapOf<Point, Boolean>()
        height+=2
        width+=2
        xx-=1
        yy-=1
        for (x in xx until xx+width) {
            for (y in yy until yy+height) {
                newGrid[Point(x, y)] = false
            }
        }
        for (entry in newGrid) {
            val point = entry.key
            var value = 0
            for (y in point.y - 1..point.y + 1) {
                for (x in point.x - 1..point.x + 1) {
                    value = value shl 1
                    if (if (grid.contains(Point(x,y))) grid[Point(x, y)] == true else enhancement[0] == '#' && i%2 == 1) {
                        value++
                    }
                }
            }
            newGrid[point] = enhancement[value] == '#'
        }
        grid = newGrid
    }
    for (y in yy until width+yy) {
        for (x in xx until height+xx) {
            print(if (grid[Point(x, y)]!!) '#' else '.')
        }
        println()
    }
    return grid.count { entry -> entry.value }
}

fun solve2(): Any? {
    val input = Solution.getInputAsText()
    val split = input.split(Regex("\\r?\\n\\r?\\n"))
    val enhancement = split[0]

    var grid = mutableMapOf<Point, Boolean>()
    for (y in split[1].lines().indices) {
        for ((x, c) in split[1].lines()[y].toList().withIndex()) {
            when (c) {
                '#' -> grid[Point(x, y)] = true
                '.' -> grid[Point(x, y)] = false
                else -> throw IllegalStateException()
            }
        }
    }

    var height = split[1].lines().count()
    var width = split[1].lines()[0].length
    var xx = 0
    var yy = 0

    for (i in 0 until 50) {
        val newGrid = mutableMapOf<Point, Boolean>()
        height+=2
        width+=2
        xx-=1
        yy-=1
        for (x in xx until xx+width) {
            for (y in yy until yy+height) {
                newGrid[Point(x, y)] = false
            }
        }
        for (entry in newGrid) {
            val point = entry.key
            var value = 0
            for (y in point.y - 1..point.y + 1) {
                for (x in point.x - 1..point.x + 1) {
                    value = value shl 1
                    if (if (grid.contains(Point(x,y))) grid[Point(x, y)] == true else enhancement[0] == '#' && i%2 == 1) {
                        value++
                    }
                }
            }
            newGrid[point] = enhancement[value] == '#'
        }
        grid = newGrid
    }
    for (y in yy until width+yy) {
        for (x in xx until height+xx) {
            print(if (grid[Point(x, y)]!!) '#' else '.')
        }
        println()
    }
    return grid.count { entry -> entry.value }
}


fun main() {
    Solution.run(20, ::solve1)
    Solution.run(20, ::solve2)
}