package solution.day8

import util.getInputAsText
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.roundToInt


fun solve1() {
    val input = getInputAsText(8).lines().map { it.split(" | ").map { it.split(' ') } }
    val res = input.sumOf {
        it[1].count {
            it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7
        }
    }
    println(res)
}

fun solve2_() {
    val input = getInputAsText(8).lines()
        .map { it.split(" | ").map { it.split(' ').map { it.toCharArray().sorted().joinToString("") } } }

    val defaultSegments = mapOf(
        0 to listOf('a', 'b', 'c', 'e', 'f', 'g'),
        1 to listOf('c', 'f'),
        2 to listOf('a', 'c', 'd', 'e', 'g'),
        3 to listOf('a', 'c', 'd', 'f', 'g'),
        4 to listOf('b', 'c', 'd', 'f'),
        5 to listOf('a', 'b', 'd', 'f', 'g'),
        6 to listOf('a', 'b', 'd', 'e', 'f', 'g'),
        7 to listOf('a', 'c', 'f'),
        8 to listOf('a', 'b', 'c', 'd', 'e', 'f', 'g'),
        9 to listOf('a', 'b', 'c', 'd', 'f', 'g')
    )

    val cableMapping = mutableMapOf<Char, List<Char>>()

    for (lineI in 0 until 1) {
        val line = input[lineI]
        val pairs = line[0].map { Pair(it, listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)) }.toTypedArray()
        val dic = mutableMapOf(*pairs)
        for (dicEntry in dic) {
            val nums = defaultSegments.filter { it.value.size == dicEntry.key.length }
            if (nums.size == 1) {
                val num = nums.toList().first()
                for (c in dicEntry.key.toCharArray()) {
                    val newlyLearned = num.second.toList()
                    cableMapping[c] = cableMapping.getOrDefault(c, newlyLearned).intersect(newlyLearned).toList()
                }

                dic[dicEntry.key] = listOf(num.first)
                for (otherEntry in dic) {
                    if (otherEntry.key != dicEntry.key) {
                        dic[otherEntry.key] = dic[otherEntry.key]!!.filter { it != num.first }
                    }
                }
            } else {
                dic[dicEntry.key] = dicEntry.value.filter { defaultSegments[it]!!.size == dicEntry.key.length }
            }
        }
        println(cableMapping);
        println(dic);


//        while (cableMapping.any {it.value.size > 1}) {
//            for (dicEntry in dic.filter { it.value.size > 1 }) {
//                for (possibleNum in dicEntry.value) {
//                    var whatWeNeed = defaultSegments[possibleNum]!!.toList()
//                    for (c in dicEntry.key.toCharArray()) {
//                        whatWeNeed = whatWeNeed.filter { cableMapping[c]!!.contains(it) }
//                    }
//                    if (whatWeNeed.size > 0) {
//                        println("welp")
//                    }
//                }
//            }
//        }
    }


}


fun solve2() {
    val input = getInputAsText(8).lines()
        .map { it.split(" | ").map { it.split(' ').map { it.toCharArray().sorted().joinToString("") } } }
    var res = 0
    for (line in input) {
        val mapping = mutableMapOf<Int, String>()
        mapping[1] = line[0].find { it.length == 2 }!!
        mapping[4] = line[0].find { it.length == 4 }!!
        mapping[7] = line[0].find { it.length == 3 }!!
        mapping[8] = line[0].find { it.length == 7 }!!

        val rest = line[0].groupBy { it.length }.filter { it.value.size > 1 }
        var fiveDigits = rest[5]
        var sixDigits = rest[6]

        mapping[9] = sixDigits!!.maxByOrNull { it.toList().intersect((mapping[7] + mapping[4]).toList()).size }!!
        sixDigits = sixDigits.filter {it != mapping[9]}
        mapping[0] = sixDigits.maxByOrNull { it.toList().intersect(mapping[1]!!.toList()).size }!!
        mapping[6] = sixDigits.filter { it != mapping[0] }.first()


        mapping[3] = fiveDigits!!.maxByOrNull { it.toList().intersect(mapping[1]!!.toList()).size }!!
        fiveDigits = fiveDigits.filter { it != mapping[3] }
        val leftTop = mapping[8]!!.toList().filterNot { mapping[6]!!.contains(it) }
        mapping[2] = fiveDigits.first { it.contains(leftTop.toList().first()) }
        mapping[5] = fiveDigits.filterNot { it == mapping[2] }.first()
        // two
        // three
        // five


        val innerRes = line[1].foldIndexed(0) { index, acc, s ->
            val m = mapping.filter { it.value == s}
            (acc + m.keys.first() * 10.0.pow((3 - index).toDouble())).roundToInt()
        }
        res += innerRes
    }
    println(res)
}




fun main() {
    solve1()
    solve2()
}

/*
 aaaa
b    c
b    c
 dddd
e    f
e    f
 gggg
 */