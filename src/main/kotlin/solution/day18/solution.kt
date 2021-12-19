package solution.day18

import util.InputType
import util.Solution
import kotlin.math.roundToInt

data class FishNumber(var nested: Pair<FishNumber, FishNumber>?, var value: Int?) {
    fun getMagnitude(): Int {
        if (nested != null) {
            return nested!!.first.getMagnitude() * 3 + nested!!.second.getMagnitude() * 2
        }
        return value!!
    }

    override fun toString(): String {
        if (nested != null) {
            return "[${nested!!.first},${nested!!.second}]"
        }
        return "$value"
    }

    fun copy(): FishNumber {
        if (nested != null) {
            return FishNumber(Pair(nested!!.first.copy(), nested!!.second.copy()), null)
        }
        return FishNumber(null, value!!)
    }
}

fun solve1(): Int {
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

    val result = values.reduce { acc, it ->
        reduceFishNumber(FishNumber(Pair(acc, it), null))
    }
    return result.getMagnitude()
}

fun solve2(): Int {
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

    val result = values.maxOf { base ->
        values.maxOf {
            if (it != base) reduceFishNumber(FishNumber(Pair(base.copy(), it.copy()), null)).getMagnitude() else 0
        }
    }
    return result
}

fun reduceFishNumber(fishNumber: FishNumber): FishNumber {
    var curr = fishNumber
    while (true) {
        val ex = explodeFishNumber(curr)
        if (ex == null) {
            val sp = splitFishNumber(curr) ?: return curr
            curr = sp
            continue
        }
        curr = ex
    }
}

fun explodeFishNumber(fishNumber: FishNumber): FishNumber? {
    val open = ArrayDeque(listOf(Pair(fishNumber, 1)))
    var lastValue: FishNumber? = null
    var addToNext: Int? = null
    while (open.isNotEmpty()) {
        val curr = open.removeFirst()
        val fs = curr.first
        val d = curr.second
        if (fs.nested != null && d > 4) {
            if (fs.nested?.first?.value != null && fs.nested?.second?.value != null) {
                val left = fs.nested!!.first
                val right = fs.nested!!.second

                val vLast = lastValue?.value
                if (vLast != null) {
                    lastValue?.value = vLast + left.value!!
                }

                addToNext = right.value!!

                var next: FishNumber? = null
                while (next?.value == null && open.isNotEmpty()) {
                    val n = open.removeFirst().first
                    if (n.nested != null) {
                        open.addAll(0, n.nested!!.toList().map { Pair(it, d + 1) })
                    } else {
                        next = n
                    }
                }
                if (next != null) {
                    next.value = next.value!! + addToNext
                }
                fs.nested = null
                fs.value = 0
                return fishNumber
            } else {
                if (fs.nested != null) open.addAll(0, fs.nested!!.toList().map { Pair(it, d + 1) })
            }
        } else if (fs.nested != null) {
            open.addAll(0, fs.nested!!.toList().map { Pair(it, d + 1) })
        } else if (fs.value != null) {
            lastValue = fs
        }
    }

    return null
}

fun splitFishNumber(fishNumber: FishNumber): FishNumber? {
    val open = ArrayDeque(listOf(fishNumber))
    while (open.isNotEmpty()) {
        val curr = open.removeFirst()
        if (curr.nested != null) {
            open.addAll(0, curr.nested!!.toList())
        } else if (curr.value!! > 9) {
            val left = curr.value!! / 2
            val right = (curr.value!! / 2.0).roundToInt()
            curr.value = null
            curr.nested = Pair(FishNumber(null, left), FishNumber(null, right))
            return fishNumber
        }
    }
    return null
}

fun main() {
    Solution.run(18, ::solve1)
    Solution.run(18, ::solve2)
}