package solution.day10

import util.getInputAsText
import kotlin.math.roundToInt

val openers = listOf('(', '[', '{', '<')
val closers = listOf(')', ']', '}', '>')

fun solve1() {
    val input = getInputAsText(10).lines().map { it.toList() }

    val errors = mutableListOf<Char>()
    lines@ for (line in input) {
        val open = ArrayDeque<Char>()
        for (c in line) {
            if (openers.contains(c)) {
                open.addLast(c)
                continue
            }
            if (open.size > 0) {
                val last = open.last()
                if (closers.indexOf(c) == openers.indexOf(last)) {
                    open.removeLast()
                    continue
                } else {
                    errors.add(c)
                    continue@lines
                }
            } else if (closers.contains(c)) {
                errors.add(c)
                continue@lines
            } else {
                open.addLast(c)
            }
        }
    }

    println(errors.count { it == ')' }*3 + errors.count { it == ']' }*57 + errors.count { it == '>' }*25137 + errors.count { it == '}' }*1197 )
}

fun solve2() {
    val input = getInputAsText(10).lines().map { it.toList() }

    val errors = mutableListOf<Char>()
    val goodLines = mutableListOf<Pair<List<Char>, ArrayDeque<Char>>>()
    lines@ for (line in input) {
        val open = ArrayDeque<Char>()
        for (c in line) {
            if (openers.contains(c)) {
                open.addLast(c)
                continue
            }
            if (open.size > 0) {
                val last = open.last()
                if (closers.indexOf(c) == openers.indexOf(last)) {
                    open.removeLast()
                    continue
                } else {
                    errors.add(c)
                    continue@lines
                }
            } else if (closers.contains(c)) {
                errors.add(c)
                continue@lines
            } else {
                open.addLast(c)
            }
        }

        goodLines.add(Pair(line, open))
    }

    val resLines = goodLines.map {
        it.second.toList().reversed().fold(0.toLong()) { acc, c ->
            acc * 5 + (openers.indexOf(c) + 1)
        }
    }
    val res = resLines.sorted().get((resLines.size/2.0 - 0.5).roundToInt());
    println(res)
}

fun main() {
    solve1()
    solve2()
}