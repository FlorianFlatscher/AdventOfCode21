package solution.day23

import util.Solution

enum class CavePart {
    WALL,
    FREE,
    A,
    B,
    C,
    D;

    fun getEnergy(): Int {
        return when (this) {
            A -> 1
            B -> 10
            C -> 100
            D -> 1000
            else -> throw IllegalStateException()
        }

    }
}

data class P(val x: Int, val y: Int)

data class CaveP(val part: CavePart, val x: Int, val y: Int)

typealias Cave = MutableMap<P, CavePart>

fun solve1(): Any? {
    val input = Solution.getInputAsText().lines().map(String::toList)
    val cave: Cave = mutableMapOf()
    val positions = mutableSetOf<CaveP>()
    input.forEachIndexed { y, l ->
        l.forEachIndexed { x, c ->
            val state = when (c) {
                '#' -> CavePart.WALL
                '.' -> CavePart.FREE
                'A' -> {
                    positions.add(CaveP(CavePart.A, x, y))
                    CavePart.A
                }
                'B' -> {
                    positions.add(CaveP(CavePart.B, x, y))
                    CavePart.B
                }
                'C' -> {
                    positions.add(CaveP(CavePart.C, x, y))
                    CavePart.C
                }
                'D' -> {
                    positions.add(CaveP(CavePart.D, x, y))
                    CavePart.D
                }
                else -> null
            }
            if (state != null) {
                cave[P(x, y)] = state
            }
        }
    }

    return trackBack(cave, positions)
}

fun trackBack(cave: Cave, positions: Set<CaveP>): Int {
    if (isCaveSorted(cave)) {
        return 0
    }

    val results = mutableListOf<Int>()
    for (p in positions) {
        val freeNeighbors = mutableSetOf<P>()
        for (pp in listOf(listOf(0,-1), listOf(1,0), listOf(0,1), listOf(-1,0))) {
            val (xx, yy) = pp
            if (xx == 0 && yy == 0) continue
            if (cave[P(p.x + xx, p.y + yy)] == CavePart.FREE) {
                freeNeighbors.add(P(p.x + xx, p.y + yy))
            }
        }
        println(freeNeighbors)

        for (f in freeNeighbors) {
            val newCave = HashMap(cave)
            newCave[P(p.x, p.x)] = CavePart.FREE
            newCave[P(f.x, f.x)] = p.part

            val newPositions = HashSet(positions)
            newPositions.remove(p)
            newPositions.add(p.copy(x = f.x, y = f.y))
            results.add(trackBack(newCave, newPositions) + p.part.getEnergy())
        }
    }

    return results.minOrNull() ?: throw IllegalStateException()
}

fun isCaveSorted(cave: Cave): Boolean {
    return cave[P(3, 2)] == CavePart.A &&
            cave[P(3, 3)] == CavePart.A &&

            cave[P(5, 2)] == CavePart.B &&
            cave[P(5, 3)] == CavePart.B &&

            cave[P(7, 2)] == CavePart.B &&
            cave[P(7, 3)] == CavePart.B &&

            cave[P(9, 2)] == CavePart.B &&
            cave[P(9, 3)] == CavePart.B
}

fun solve2(): Any? {
    return null
}

fun main() {
    Solution.run(23, ::solve1)
}