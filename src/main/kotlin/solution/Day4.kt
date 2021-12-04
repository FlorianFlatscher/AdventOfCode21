package solution

import util.getInputAsText

data class Board(val values: List<List<Int>>, val marked: MutableList<MutableList<Boolean>>)


fun solve1() {
    val input = getInputAsText(4)
    val moves = input.lines()[0].split(",").map { it.toInt() }

    val boards = input.lines().subList(1, input.lines().size).chunked( 6) { it.drop(1)}.map { list ->
        val values = list.map { it.trim().split(Regex("\\s+")).map { it.toInt() } }
        Board(values, values.map { it.map { false }.toMutableList() }.toMutableList())
    }

    var result: Board? = null
    var lastNumber = moves[0]
    for (number in moves) {
        lastNumber = number
        // mark numbers
        boards.forEach { board ->
            val values = board.values
            for ((x) in values.withIndex()) {
                for ((y) in values[x].withIndex()) {
                    if (values[x][y] == number) {
                        board.marked[x][y] = true
                    }
                }
            }
        }

        // check if board has won
        // vertical
        val winner = boards.find { board ->
            board.marked.any { it.all { it } } ||
                    board.marked.reduce { acc, curr ->
                        acc.mapIndexed { index, b ->
                            b && curr[index]
                        }.toMutableList()
                    }.any { it }
        }

        if (winner != null) {
            result = winner
            break
        }
    }
    result!!
    var sum = 0
    result.values.forEachIndexed { x, list ->
        list.forEachIndexed { y, i ->
            if (!result.marked[x][y]) {
                sum += i
            }
        }
    }
    println(sum * lastNumber)
}

fun solve2() {
    val input = getInputAsText(4)
    val moves = input.lines()[0].split(",").map { it.toInt() }

    var boards = input.lines().subList(1, input.lines().size).chunked( 6) { it.drop(1)}.map { list ->
        val values = list.map { it.trim().split(Regex("\\s+")).map { it.toInt() } }
        Board(values, values.map { it.map { false }.toMutableList() }.toMutableList())
    }

    var result: Board? = null
    var lastNumber = moves[0]

    for (number in moves) {
        lastNumber = number
        // mark numbers
        boards.forEach { board ->
            val values = board.values
            for ((x) in values.withIndex()) {
                for ((y) in values[x].withIndex()) {
                    if (values[x][y] == number) {
                        board.marked[x][y] = true
                    }
                }
            }
        }

        // check if board has won
        // vertically
        if (boards.size > 1) {
            boards = boards.filter { !isBoardComplete(it) }
        } else if (isBoardComplete(boards[0])) {
            result = boards[0]
            break
        }
    }
    result!!
    var sum = 0
    result.values.forEachIndexed { x, list ->
        list.forEachIndexed { y, i ->
            if (!result.marked[x][y]) {
                sum += i
            }
        }
    }
    println(sum * lastNumber)
}

fun isBoardComplete (board: Board): Boolean {
    return board.marked.any { it.all { it } } ||
            board.marked.reduce { acc, curr ->
                acc.mapIndexed { index, b ->
                    b && curr[index]
                }.toMutableList()
            }.any { it }
}

fun main() {
    solve1()
    solve2()
}