package solution.day12

import util.getInputAsText
import java.util.*

data class Cave(val name: String) {
    val isStart = name == "start"
    val isEnd = name == "end"
    val isUpperCase = !isStart && !isEnd && name.all { it.isUpperCase() }
    val isLowerCase = !isStart && !isEnd && name.all { it.isLowerCase() }
}

class PathNode(val cave: Cave, val from: PathNode?) {
    var tos: MutableSet<PathNode> = mutableSetOf()

    fun isValidTo(to: Cave): Boolean {
        if (to.isStart || to == this.cave) {
            return false
        }
        if (from == null || to.isEnd || to.isUpperCase) {
            return true
        }
        val path = getPathToThis()

        return !path.any { it.cave.name == to.name }
    }

    fun isValidToLevel2(to: Cave): Boolean {
        if (to.isStart || to == this.cave) {
            return false
        }
        if (from == null || to.isEnd || to.isUpperCase) {
            return true
        }
        val path = getPathToThis()

        val duplicatesInPath = path.any{ outer -> outer.cave.isLowerCase && 1 < path.count { inner -> outer.cave == inner.cave }}
        val duplicatesWithNext = path.any { outer -> (outer.cave == to) }
        return !(duplicatesWithNext && duplicatesInPath)
    }

    fun getPathToThis(): List<PathNode> {
        val path = mutableListOf(this)
        var next = from
        while (next != null) {
            path.add(next)
            next = next.from
        }
        return path
    }

    override fun toString(): String {
        return "PathNode(cave=$cave, tos.size=${tos.size})"
    }
}

fun solve1() {
    val input = getInputAsText(12)
    val graph = mutableMapOf<Cave, List<Cave>>()

    for (line in input.lines()) {
        val parts = line.split('-')
        graph[Cave(parts[0])] = graph.getOrDefault(Cave(parts[0]), listOf()) + Cave(parts[1])
        graph[Cave(parts[1])] = graph.getOrDefault(Cave(parts[1]), listOf()) + Cave(parts[0])
    }

    val initialNode = PathNode(Cave("start"), null)
    initialNode.tos.addAll(graph[Cave("start")]!!.map { PathNode(it, initialNode) })

    val open = ArrayDeque<PathNode>(initialNode.tos.size)
    open.addAll(initialNode.tos)

    while (open.isNotEmpty()) {
        val next = open.removeFirst()
        val candidates = graph[next.cave]
        if (candidates != null) {
            for (c in candidates) {
                if (next.isValidTo(c)) {
                    val pn = PathNode(c, next)
                    next.tos.add(pn)
                    if (!c.isEnd) {
                        open.add(pn)
                    }
                }
            }
        }
    }

    val tos = ArrayDeque(listOf(initialNode))
    var count = 0
    while (tos.isNotEmpty()) {
        val next = tos.removeFirst();
        for (to in next.tos) {
            if (to.cave.name == "end") {
                count++
            } else {
                tos.add(to)
            }
        }
    }
    println(count)
}

fun solve2() {
    val input = getInputAsText(12)
    val graph = mutableMapOf<Cave, List<Cave>>()

    for (line in input.lines()) {
        val parts = line.split('-')
        graph[Cave(parts[0])] = graph.getOrDefault(Cave(parts[0]), listOf()) + Cave(parts[1])
        graph[Cave(parts[1])] = graph.getOrDefault(Cave(parts[1]), listOf()) + Cave(parts[0])
    }

    val initialNode = PathNode(Cave("start"), null)
    initialNode.tos.addAll(graph[Cave("start")]!!.map { PathNode(it, initialNode) })

    val open = ArrayDeque<PathNode>(initialNode.tos.size)
    open.addAll(initialNode.tos)

    while (open.isNotEmpty()) {
        val next = open.removeFirst()
        val candidates = graph[next.cave]
        if (candidates != null) {
            for (c in candidates) {
                if (next.isValidToLevel2(c)) {
                    val pn = PathNode(c, next)
                    next.tos.add(pn)
                    if (!c.isEnd) {
                        open.add(pn)
                    }
                }
            }
        }
    }

    val tos = ArrayDeque(listOf(initialNode))
    var count = 0
    while (tos.isNotEmpty()) {
        val next = tos.removeFirst();
        for (to in next.tos) {
            if (to.cave.name == "end") {
                count++
            } else {
                tos.add(to)
            }
        }
    }
    println(count)
}

fun main() {
    solve1()
    solve2()
}