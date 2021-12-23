package solution.day23

import util.Solution

enum class CavePart {
    WALL,
    FREE,
    A,
    B,
    C,
    D;

    fun getEnergy(): Long {
        return when (this) {
            A -> 1
            B -> 10
            C -> 100
            D -> 1000
            else -> throw IllegalStateException()
        }
    }

    fun getDestinationX(): Int {
        return when (this) {
            A -> 3
            B -> 5
            C -> 7
            D -> 9
            else -> throw IllegalStateException()
        }
    }

    override fun toString(): String {
        return when (this) {
            WALL -> "#"
            FREE -> "."
            A -> "A"
            B -> "B"
            C -> "C"
            D -> "D"
        }
    }
}

data class P(val x: Int, val y: Int)

data class Cave(val rooms: List<List<CavePart?>>, val hallway: List<CavePart> = (0..4).map { CavePart.FREE }.toList())

//fun printCave(cave: Cave): String {
//    val base = "#############\n#...........#\n###B#C#B#D###\n  #A#D#C#A#\n  #########".lines().toMutableList()
//    for (entry in cave) {
//        base[entry.key.y] = "${
//            base[entry.key.y].substring(
//                0,
//                entry.key.x
//            )
//        }${entry.value}${base[entry.key.y].substring(entry.key.x + 1)}"
//    }
//    return base.joinToString("\n")
//}

fun solve1(): Any? {
    val input = Solution.getInputAsText().lines().map(String::toList)
    val rooms = (0..3).map { mutableListOf<CavePart>() }.toList()

    input.forEachIndexed { y, l ->
        l.forEachIndexed { x, c ->
            val state = when (c) {
                'A' -> {
                    CavePart.A
                }
                'B' -> {
                    CavePart.B
                }
                'C' -> {
                    CavePart.C
                }
                'D' -> {
                    CavePart.D
                }
                else -> null
            }
            if (state != null) {
                rooms[(y - 3) / 2].add(state)
            }
        }
    }

    val cave = Cave(rooms)
    return trackBack(cave)
}

val cache = mutableSetOf<Cave>()

fun trackBack(cave: Cave): Int {
    if (!cache.add(cave)) {
        return Int.MAX_VALUE
    }
    if (isCaveSorted(cave)) {
        return 0
    }

    val results = mutableListOf<Long>()

    for ((roomIndex, room) in cave.rooms.withIndex()) {
        val index = room.indexOfFirst { it != null }
        if (index >= 0) {
            val freeHallwayPositions = mutableSetOf<Int>()
            val next = mutableListOf(roomIndex, roomIndex + 1)
            while (next.isNotEmpty()) {
                val c = next.removeFirst()
                if (cave.hallway[c - 1] == null) {
                    freeHallwayPositions.add(c - 1)
                    if (!next.contains(c - 1)) {
                        next.add(c - 1)
                    }
                }
                if (cave.hallway[c + 1] == null) {
                    freeHallwayPositions.add(c + 1)
                    if (!next.contains(c + 1)) {
                        next.add(c + 1)
                    }
                }
            }

            for (f in freeHallwayPositions) {
                val newRooms = cave.rooms.map { it.map { it }.toMutableList() }.toMutableList()
                newRooms[roomIndex][index] = null

                val newHallway = cave.hallway.map { it }.toMutableList()
                newHallway[f] =
                newPositions.remove(p)
                newPositions.add(p.copy(x = f.x, y = f.y))

                results.add(trackBack(newCave, newPositions, d + 1).toLong() + p.part.getEnergy())
            }
        }
    }
//    for (p in toMove) {
//        val freeNeighbors = mutableSetOf<P>()
//        for (pp in listOf(listOf(0, -1), listOf(1, 0), listOf(0, 1), listOf(-1, 0))) {
//            val (xx, yy) = pp
//            if (xx == 0 && yy == 0) continue
//            val x = p.x + xx
//            val y = p.y + yy
//            if (cave[P(x, y)] == CavePart.FREE) {
//                // Check go into room rule
//                if (!(p.y == 1 && y in listOf(2, 3) && x in listOf(
//                        3,
//                        5,
//                        7,
//                        9
//                    )) || (p.part.getDestinationX() == x && listOf(2, 3).all {
//                        cave[P(
//                            p.part.getDestinationX(),
//                            it
//                        )] in (listOf(p.part, CavePart.FREE))
//                    })
//                ) {
//                    freeNeighbors.add(P(x, y))
//                }
//            }
//        }

    for (f in freeNeighbors) {
        val newCave = HashMap(cave)
        newCave[P(p.x, p.y)] = CavePart.FREE
        newCave[P(f.x, f.y)] = p.part

        val newPositions = HashSet(positions)
        newPositions.remove(p)
        newPositions.add(p.copy(x = f.x, y = f.y))

        results.add(trackBack(newCave, newPositions, d + 1).toLong() + p.part.getEnergy())
    }
}

return results.minOrNull()?.toInt() ?: Int.MAX_VALUE
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