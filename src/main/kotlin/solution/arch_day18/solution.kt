package solution.arch_day18

import util.InputType
import util.Solution
import kotlin.math.roundToInt

fun solve1(): String? {
    val input = Solution.getInputAsText().lines()

    var first = input.first()
    var second = input[1]
    val res = reduceFishNumber("[[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]")
    println(res)
//    return explodeFishNumber(res)
    return ""
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

fun explodeFishNumber(fishNumber: String): Pair<String, Int>? {
    val open = ArrayDeque<Char>()
    val numbers = ArrayDeque<Int>()

    val iterator = fishNumber.iterator()
    var i = -1
    var nextToUse = ArrayDeque<Char>()
    while (iterator.hasNext()) {
        i++
        val c = nextToUse.removeFirstOrNull() ?: iterator.next()
        if (c == '[') {
            if (open.size >= 4) {
                var c1 = 0
                var save: Char? = null
                while (iterator.hasNext()) {
                    val n = iterator.next()
                    if (n.isDigit()) {
                        c1 *= 10
                        c1 += n.digitToInt()
                    } else {
                        if (c1 == 0) {
                            nextToUse.add(n)
                            continue
                        }
                        save = n
                        break
                    }
                }

                var c2 = 0
                while (iterator.hasNext()) {
                    val n = iterator.next()
                    if (n.isDigit()) {
                        c1 *= 10
                        c1 += n.digitToInt()
                    } else {
                        if (c1 == 0) {
                            nextToUse.add(c1.toChar())
                            if (save != null) {
                                nextToUse.add(save)
                            }
                            nextToUse.add(n)
                            continue
                        }
                        break
                    }
                }

                if (iterator.next().isDigit()) {
                    return null
                }


                val n1 = c1
                val n2 = c2

                var next = -1
                while (iterator.hasNext()) {
                    val n = iterator.next()
                    if (n.isDigit() && next >= 0) {
                        next *= 10
                        next += n.digitToInt()
                    } else if (n.isDigit() && next < 0) {
                        next = n.digitToInt()
                    } else if (!n.isDigit() && next >= 0) {
                        break
                    }
                }

                var last = -1
                while (last < 0 && numbers.size > 0) {
                    val n = numbers.removeLast()
                    last = n
                }

                val newString =
                    fishNumber.substring(0, i).reversed()
                        .replaceFirst(last.toString().reversed(), (n1 + last).toString().reversed())
                        .reversed() +
                            "0" +
                            fishNumber.substring(i + 3 + n1.toString().length +n2.toString().length).replaceFirst(next.toString(), (n2 + next).toString())

                return Pair(newString, i + 5)
            }
            open.add(c)
        } else if (c == ']') {
            open.removeLastOrNull()
        } else if (c != ',') {
            numbers.add(c.digitToInt())
        } else {
            numbers.add(-1)
        }
    }

    return null
}

fun splitFishNumber(fishNumber: String): Pair<String, Int>? {
    val match = Regex("\\d{2,}").find(fishNumber) ?: return null
    val n = match.value.toInt()
    val left = n / 2
    val right = (n / 2.0 + 0.5).roundToInt()

    return Pair(fishNumber.replaceFirst(n.toString(), "[$left,$right]"), match.range.last)
}

fun main() {
    Solution.run(18, ::solve1, InputType.SAMPLE)
}