package solution.day6

import util.getInputAsText
import java.util.*


private fun solve1() {
    val input = getInputAsText(6).split(",").map { it.toInt() }
    val fishPod = LongArray(9)

    input.map {
        fishPod[it] = fishPod[it]+1
    }

    for (day in 0 until 256  ) {
        val count8s = fishPod[0]

        for (i in 1 until fishPod.size) {
            fishPod[i - 1] = fishPod[i]
        }
        fishPod[fishPod.size - 1] = 0

        fishPod[6] += count8s
        fishPod[8] += count8s
    }

    println(fishPod.sum())
}

fun main() {
    solve1()
}

