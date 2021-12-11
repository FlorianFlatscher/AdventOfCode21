package solution.day11

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

data class PointData(val charge: Int, val hasFlashed: Boolean) {
    constructor(pd: PointData) : this(pd.charge, pd.hasFlashed)
}

fun solve1() {
    val input = getInputAsText(11).lines().map { it.toList().map { it.digitToInt() } }
    val grid = hashMapOf<Point, PointData>()

    for (y in input.indices) {
        for (x in input[y].indices) {
            grid[Point(x, y)] = PointData(input[x][y], false)
        }
    }

    var currentGrid = HashMap(grid);
    var flashCount = 0
    for (i in 0..194) {
        val nextGrid = HashMap(currentGrid)
        nextGrid.entries.forEach { entry -> entry.setValue(PointData(entry.value.charge + 1, false)) }

        var newlyFlashed = true
        while (newlyFlashed) {
            newlyFlashed = false
            for (entry in nextGrid) {
                if (entry.value.charge > 9 && !entry.value.hasFlashed) {
                    flashCount++
                    entry.setValue(PointData(entry.value.charge, true))
                    for (xx in -1..1) {
                        for (yy in -1..1) {
                            if (xx == 0 && yy == 0) {
                                continue
                            }
                            val x = entry.key.x + xx
                            val y = entry.key.y + yy
                            val n = nextGrid[Point(x, y)]
                            if (n != null) {
                                if (n.charge == 9) {
                                    newlyFlashed = true
                                }
                                nextGrid[Point(x, y)] = PointData(n.charge + 1, n.hasFlashed)
                            }
                        }
                    }
                }
            }
        }

        nextGrid.entries.forEach { entry -> entry.setValue(PointData(if (!entry.value.hasFlashed) entry.value.charge else 0, false)) }

        currentGrid = nextGrid
    }

    println(flashCount)
}

fun solve2() {
    val input = getInputAsText(11).lines().map { it.toList().map { it.digitToInt() } }
    val grid = hashMapOf<Point, PointData>()

    for (y in input.indices) {
        for (x in input[y].indices) {
            grid[Point(x, y)] = PointData(input[x][y], false)
        }
    }

    var currentGrid = HashMap(grid);
    for (i in 0..Int.MAX_VALUE) {
        val nextGrid = HashMap(currentGrid)
        nextGrid.entries.forEach { entry -> entry.setValue(PointData(entry.value.charge + 1, false)) }

        var flashCount = 0
        var newlyFlashed = true
        while (newlyFlashed) {
            newlyFlashed = false
            for (entry in nextGrid) {
                if (entry.value.charge > 9 && !entry.value.hasFlashed) {
                    flashCount++
                    entry.setValue(PointData(entry.value.charge, true))
                    for (xx in -1..1) {
                        for (yy in -1..1) {
                            if (xx == 0 && yy == 0) {
                                continue
                            }
                            val x = entry.key.x + xx
                            val y = entry.key.y + yy
                            val n = nextGrid[Point(x, y)]
                            if (n != null) {
                                if (n.charge == 9) {
                                    newlyFlashed = true
                                }
                                nextGrid[Point(x, y)] = PointData(n.charge + 1, n.hasFlashed)
                            }
                        }
                    }
                }
            }

        }

        if (flashCount == nextGrid.size) {
            println(i+1)
            return
        }

        nextGrid.entries.forEach { entry -> entry.setValue(PointData(if (!entry.value.hasFlashed) entry.value.charge else 0, false)) }

        currentGrid = nextGrid
    }

}

fun main() {
    solve1()
    solve2()
}