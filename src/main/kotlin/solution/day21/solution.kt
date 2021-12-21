package solution.day21

import util.Solution

fun solve1(): Any? {
    val input = Solution.getInputAsText()

    var p1Position = input.lines()[0].substringAfterLast(' ').toInt() - 1
    var p2Position = input.lines()[1].substringAfterLast(' ').toInt() - 1

    var p1Score = 0
    var p2Score = 0

    var i = 0
    var player1 = true
    while (true) {
        i += 3


        var a = i
        if (a > 100) {
            a = a % 100
        }
        if (a == 0) {
            a = 100
        }
        var b = i - 1
        if (b > 100) {
            b = b % 100
        }
        if (b == 0) {
            b = 100
        }
        var c = i - 2
        if (c > 100) {
            c = c % 100
        }
        if (c == 0) {
            c = 100
        }

        val die = a + b + c

        if (player1) {
            p1Position += die
            p1Position %= 10

            p1Score += p1Position + 1
            if (p1Score >= 1000) {
                println(p1Score)
                return p2Score * i
            }
        } else {
            p2Position += die
            p2Position %= 10

            p2Score += p2Position + 1
            if (p2Score >= 1000) {
                println(p2Score)

                return p1Score * i
            }
        }

        player1 = !player1
    }
}

fun solve2(): Any? {
    val input = Solution.getInputAsText()

    var p1Position = input.lines()[0].substringAfterLast(' ').toLong() - 1
    var p2Position = input.lines()[1].substringAfterLast(' ').toLong() - 1

    return playQuantom(0, Pair(p1Position, 0), Pair(p2Position, 0)).toList().maxOrNull()
}

val cache = mutableMapOf<Pair<Pair<Long, Long>, Pair<Long, Long>>, Pair<Long, Long>>()

fun playQuantom(i: Long, playerA: Pair<Long, Long>, playerB: Pair<Long, Long>): Pair<Long, Long> {

    cache[Pair(playerA, playerB)]?.let { return it }

    val dices = sequence {
        for (a in 1..3) {
            for (b in 1..3) {
                for (c in 1..3) {
                    yield(listOf(a, b, c).sum())
                }
            }
        }
    }

    println(i)

    val newPositions = dices.map {
        var newPosition = playerA.first + it
        newPosition %= 10

        val newScore = playerA.second + newPosition + 1

        if (newScore >= 21) {
            return@map null
        }

        Pair(newPosition, newScore)
    }

    val universes = newPositions.filterNotNull().map {
        playQuantom(i + 1, playerB, it)
    }.map {
        Pair(it.second, it.first)
    }

    val ended = newPositions.count { it == null }

    val res = Pair(universes.sumOf { it.first } + ended, universes.sumOf { it.second })
    cache[Pair(playerA,playerB)] = res
    return res
}

fun main() {
    Solution.run(21, ::solve1)
    Solution.run(21, ::solve2)
}