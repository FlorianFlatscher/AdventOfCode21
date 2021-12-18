package solution.day18

import util.InputType
import util.Solution
import kotlin.math.roundToInt

data class FishNumber(var nested: Pair<FishNumber, FishNumber>?, var value: Int?) {
    override fun toString(): String {
        if (nested != null) {
            return "FS($nested)"
        }
        return "FS($value)"
    }
}

fun solve1(): Pair<String, Int>? {
    val input = Solution.getInputAsText().lines()


    val values = input.map {
        val open = ArrayDeque<Char>()
        val values = ArrayDeque<FishNumber>()
        it.forEach { c ->
            if (c == '[') {
                open.add('[')
            } else if (c == ']') {
                val right = values.removeLast()
                val left = values.removeLast()
                values.add(FishNumber(Pair(left, right), null))
                open.removeLast()
            } else if (c.isDigit()) {
                values.add(FishNumber(null, c.digitToInt()))
            }
        }
        values.last()
    }
    return splitFishNumber(values.first())
}

fun reduceFishNumber(fishNumber: String): String {
    var curr = fishNumber
    while (true) {
        val ex = explodeFishNumber(curr)
        val sp = splitFishNumber(curr)
        print("cu ")
        println(curr)
        print("ex")
        println(ex)
        print("sp")
        println(sp)
        curr = listOfNotNull(ex, sp).firstOrNull()?.first ?: return curr
        println("--------------------------------")

    }
}

fun explodeFishNumber(fishNumber: FishNumber): Pair<String, Int>? {
    val open = ArrayDeque(listOf(Pair(fishNumber, 1)))

    while (open.isNotEmpty()) {
        val curr = open.removeFirst()
        val fs = curr.first
        val d = curr.second
        if (fs.nested != null) {
            open.addAll(fs.nested!!.toList().map { Pair(it, d + 1) })
        } else if (d >= 4) {

        }
    }
    return null
}

fun splitFishNumber(fishNumber: FishNumber): FishNumber {
    val open = ArrayDeque(listOf(fishNumber))
    while (open.isNotEmpty()) {
        val curr = open.removeFirst()
        if (curr.nested != null) {
            open.addAll(curr.nested!!.toList())
        } else if (curr.value!! > 9) {
            val left = curr.value!! / 2
            val right = (curr.value!! / 2.0 + 0.5).roundToInt()
            curr.value = null
            curr.nested = Pair(FishNumber(null, left), FishNumber(null, right))
        }
    }
    return fishNumber
}

fun main() {
    Solution.run(18, ::solve1, InputType.SAMPLE)
}