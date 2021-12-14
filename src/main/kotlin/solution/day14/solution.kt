package solution.day14

import util.InputType
import util.Solution
import java.lang.StringBuilder
import kotlin.math.pow
import kotlin.math.roundToInt

data class Rule(val pair: String, val toInsert: Char) {
    companion object {
        fun fromLine(line: String): Rule {
            val (s1, s2) = Regex("([A-Z]{2}) -> ([A-Z])").matchEntire(line)!!.destructured
            return Rule(s1, s2.toList().first())
        }
    }
}

fun solve1(): Any? {
    val input = Solution.getInputAsText()

    val split = input.split(Regex("\\r?\\n\\r?\\n"))
    val startingPolymer = split[0]
    val inputRules = split[1].lines().map { Rule.fromLine(it) }

    var rules = hashMapOf<String, String>()
    inputRules.forEach {
        rules[it.pair] = "${it.pair[0]}${it.toInsert}${it.pair[1]}"
    }

    var polymers = mutableMapOf<String, Long>()

    startingPolymer.windowed(2, 1) {
        polymers[it.toString()] = polymers.getOrDefault(it, 0) + 1
    }

    for (i in 0..9) {
        var newPolymers = mutableMapOf<String, Long>()

        polymers.forEach {
            val newPolymer = rules[it.key]
            val left = "${newPolymer!![0]}${newPolymer[1]}"
            val right = "${newPolymer[1]}${newPolymer[2]}"
            newPolymers[left] = newPolymers.getOrDefault(left, 0) + it.value
            newPolymers[right] = newPolymers.getOrDefault(right, 0) + it.value
        }

        polymers = newPolymers
    }
//    return currentPolymers.maxOf { currentPolymers.count { i -> i == it } } - currentPolymers.minOf { currentPolymers.count { i -> i == it } }
    var list = polymers.toList()
    var pairs = mutableMapOf(*(list.groupBy { it.first[1] }.map { Pair(it.key, it.value.sumOf { it.second }) }.toTypedArray()))
    pairs[startingPolymer[0]] = pairs.getOrDefault(startingPolymer[0], 0) + 1
    return pairs.toList().maxOf {it.second} - pairs.toList().minOf {it.second}
}

//fun solve2(): Any? {
//    val input = Solution.getInputAsText()
//
//    val split = input.split(Regex("\\r?\\n\\r?\\n"))
//    var currentPolymers = StringBuilder(split[0])
//    val inputRules = split[1].lines().map { Rule.fromLine(it) }
//
//    var rulesLookUp = hashMapOf<String, String>()
//    inputRules.forEach {
//        rulesLookUp[it.pair] = "${it.pair[0]}${it.toInsert}${it.pair[1]}"
//    }
//
//    var currRules = inputRules.map { it.pair };
//    for (i in 0..30) {
//        println(currRules[0])
//        currRules = currRules.map { r ->
//            if (r.length == 2) {
//                return@map rulesLookUp[r]!!
//            }
//            var left = r.subSequence(0, (r.length/2 + 1.5).toInt())
//            var right = r.subSequence((r.length/2 + 0.5).toInt(), r.length)
////            var insetStart = rulesLookUp["${r[0]}${r[1]}"]
//            var insetLeft = rulesLookUp[left]
//            var insetRight = rulesLookUp[right]
////            var insetEnd = rulesLookUp["${r[r.length-2]}${r[r.length-1]}"]
//            var newS = insetLeft + insetRight!!.substring(1)
//            rulesLookUp[r] = newS
//            return@map newS
//        }
//    }
//
//
//    for (i in 0..0) {
//
//        var newPolymers = StringBuilder()
//
//        currentPolymers.windowed(2, 1) {
//            val rule = rulesLookUp[it]
//            if (rule != null) {
//                newPolymers.append(it[0], rule)
//            }
//        }
//
//        newPolymers.append(currentPolymers.last())
//
//        currentPolymers = newPolymers
//        println(currentPolymers.toString())
//
//    }
//    return currentPolymers.maxOf { currentPolymers.count { i -> i == it } } - currentPolymers.minOf { currentPolymers.count { i -> i == it } }
//}

fun solve2(): Any? {
    val input = Solution.getInputAsText()

    val split = input.split(Regex("\\r?\\n\\r?\\n"))
    val startingPolymer = split[0]
    val inputRules = split[1].lines().map { Rule.fromLine(it) }

    var rules = hashMapOf<String, String>()
    inputRules.forEach {
        rules[it.pair] = "${it.pair[0]}${it.toInsert}${it.pair[1]}"
    }

    var polymers = mutableMapOf<String, Long>()

    startingPolymer.windowed(2, 1) {
        polymers[it.toString()] = polymers.getOrDefault(it, 0) + 1
    }

    for (i in 0..39) {
        var newPolymers = mutableMapOf<String, Long>()

        polymers.forEach {
            val newPolymer = rules[it.key]
            val left = "${newPolymer!![0]}${newPolymer[1]}"
            val right = "${newPolymer[1]}${newPolymer[2]}"
            newPolymers[left] = newPolymers.getOrDefault(left, 0) + it.value
            newPolymers[right] = newPolymers.getOrDefault(right, 0) + it.value
        }

        polymers = newPolymers
    }
//    return currentPolymers.maxOf { currentPolymers.count { i -> i == it } } - currentPolymers.minOf { currentPolymers.count { i -> i == it } }
    var list = polymers.toList()
    var pairs = mutableMapOf(*(list.groupBy { it.first[1] }.map { Pair(it.key, it.value.sumOf { it.second }) }.toTypedArray()))
    pairs[startingPolymer[0]] = pairs.getOrDefault(startingPolymer[0], 0) + 1
    return pairs.toList().maxOf {it.second} - pairs.toList().minOf {it.second}
}

fun main() {
//    Solution.run(14, ::solve1)
    Solution.run(14, ::solve1)
    Solution.run(14, ::solve2)
}