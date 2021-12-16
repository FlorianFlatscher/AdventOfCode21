package solution.day16

import util.InputType
import util.Solution
import java.util.*

fun solve1(): Any? {
    val input = Solution.getInputAsText()

    val p = BitSet(input.length * 4)
    for ((index, c) in input.withIndex()) {
        val d = c.digitToInt(16).toString(2)

        for ((ii, cc) in d.reversed().withIndex()) {
            if (cc == '1') {
                p.set((index * 4) + 3 - ii)
            }
        }
    }

    val res = processPacket(toBinaryString(p, 0, p.size()))
    return res.first
}

// TODO
fun processPacket(packet: String): Pair<Long, Int> {
    val p = BitSet(packet.length)
    for ((index, c) in packet.withIndex()) {
        if (c == '1') {
            p.set(index)
        }
    }
    val version = toBinaryString(p, 0, 3).toLong(2)
    val type = toBinaryString(p, 3, 6).toInt(2)

    if (type == 4) {
        var startingPos = 6
        var currPos = startingPos
        var res = ""
        while (p.get(currPos)) {
            res += toBinaryString(p, currPos+1, currPos+5)
            currPos += 5
        }
        res += toBinaryString(p, currPos+1, currPos+5)
        currPos += 5
        return Pair(res.toLong(2), currPos)
    }

    val i = p.get(6)

    var subConsumed = 0
    var subValues = mutableListOf<Long>()

    if (i) {
        var currPos = 18
        var numSubPackets = toBinaryString(p, 7, 18).toInt(2)
        while (numSubPackets > 0) {
            val subPacket = toBinaryString(p, currPos, p.size())
            val res = processPacket(subPacket)
            val v = res.first
            subValues.add(v)
            val consumed = res.second
            currPos += consumed
            subConsumed = currPos
            numSubPackets -= 1
        }
    } else {
        val subPacketLength = toBinaryString(p, 7, 22).toInt(2)
        var currPos = 22
        while (currPos < subPacketLength + 22) {
            val subPacket = toBinaryString(p, currPos, p.size())
            val res = processPacket(subPacket)
            val v = res.first
            subValues.add(v)
            val consumed = res.second
            currPos += consumed
            subConsumed = currPos
        }
    }
    return when (type) {
        0 -> Pair(subValues.sum(), subConsumed)
        1 -> Pair(subValues.reduce {acc, it -> acc * it}, subConsumed)
        2 -> Pair(subValues.minOrNull()!!, subConsumed)
        3 -> Pair(subValues.maxOrNull()!!, subConsumed)
        5 -> Pair(if (subValues[0] > subValues[1]) 1 else 0, subConsumed)
        6 -> Pair(if (subValues[0] < subValues[1]) 1 else 0, subConsumed)
        7 -> Pair(if (subValues[0] == subValues[1]) 1 else 0, subConsumed)
        else -> throw IllegalStateException()
    }
}

fun toBinaryString(bs: BitSet, lower: Int, upper: Int): String {
    val sb = StringBuilder(bs.size())
    for (i in lower until upper) sb.append(if (bs[i]) 1 else 0)
    return sb.toString()
}

fun hexStringToBitSet(hexString: String): BitSet? {
    return BitSet.valueOf(longArrayOf(java.lang.Long.valueOf(hexString.substring(2), 16)))
}

fun main() {
    Solution.run(16, ::solve1)
}