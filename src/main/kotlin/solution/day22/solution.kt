package solution.day22

import util.InputType
import util.Solution

data class Operation(val target: Boolean, val x: LongRange, val y: LongRange, val z: LongRange)
data class State(val x: LongRange, val y: LongRange, val z: LongRange)
data class Point(val x: LongRange, val y: LongRange, val z: LongRange)

fun solve1(): Any? {
    val input = Solution.getInputAsText().lines()

    val operations = input.map {
        val (target, x, y, z) = Regex("(on|off) x=(.*?),y=(.*?),z=(.*?)\\s*$").find(it)!!.destructured
        val xRange = x.substringBefore("..").toLong()..x.substringAfter("..").toLong()
        val yRange = y.substringBefore("..").toLong()..y.substringAfter("..").toLong()
        val zRange = z.substringBefore("..").toLong()..z.substringAfter("..").toLong()
        Operation(target == "on", xRange, yRange, zRange)
    }

    val reactor = mutableSetOf<State>()

    operations.forEach { op ->
        val toAdd = mutableSetOf<State>()
        val toRemove = mutableSetOf<State>()
        reactor.forEach inner@{
            val xC = (it.x.first in op.x && it.x.last in op.x)
            val xO = op.x.first in it.x || op.x.last in it.x || xC
            val yC = (it.y.first in op.y && it.y.last in op.y)
            val yO = op.y.first in it.y || op.y.last in it.y || yC
            val zC = (it.z.first in op.z && it.z.last in op.z)
            val zO = op.z.first in it.z || op.z.last in it.z || zC

            if (xC && yC && zC) {
                toRemove.add(it)
                return@inner
            }

            if (xO && yO && zO) {
                var remainingX = it.x
                if (op.x.last < it.x.last) {
                    val xNew = op.x.last + 1..it.x.last
                    toAdd.add(State(xNew, it.y, it.z))
                    remainingX = remainingX.first..op.x.last
                }
                if (it.x.first < op.x.first) {
                    val xNew = it.x.first until op.x.first
                    toAdd.add(State(xNew, it.y, it.z))
                    remainingX = op.x.first..remainingX.last
                }

                var remainingY = it.y
                if (op.y.last < it.y.last) {
                    val yNew = op.y.last + 1..it.y.last
                    toAdd.add(State(remainingX, yNew, it.z))
                    remainingY = remainingY.first..op.y.last
                }
                if (it.y.first < op.y.first) {
                    val yNew = it.y.first until op.y.first
                    toAdd.add(State(remainingX, yNew, it.z))
                    remainingY = op.y.first..remainingY.last
                }

                if (op.z.last < it.z.last) {
                    val zNew = op.z.last + 1..it.z.last
                    toAdd.add(State(remainingX, remainingY, zNew))
                }
                if (it.z.first < op.z.first) {
                    val zNew = it.z.first until op.z.first
                    toAdd.add(State(remainingX, remainingY, zNew))
                }


                toRemove.add(it)
//                toAdd.addAll(listOf(xx, yy, zz))
            }
        }
        if (op.target) {
            toAdd.add(State(op.x, op.y, op.z))
        }


        reactor.removeAll(toRemove)
        reactor.addAll(toAdd)
    }

//    operations.forEach {
//        for (x in it.x) {
//            for (y in it.y) {
//                for (z in it.z) {
//                    if (it.target) {
//                        reactor.add(Point(x, y, z))
//                    } else {
//                        reactor.remove(Point(x,y,z))
//                    }
//                }
//            }
//        }
//    }

//    var count = 0
//    for (x in -500000..500000) {
//        for (y in -500000..500000) {
//            for (z in -500000..500000) {
//                val res = operations.firstOrNull { x in it.x && y in it.y && z in it.z }
//                if (res != null && res.target) {
//                    count++
//                }
//            }
//        }
//    }

    return reactor.sumOf {
        it.x.count().toLong() * it.y.count().toLong() * it.z.count().toLong()
    }
}

fun main() {
    Solution.run(22, ::solve1)
//    Solution.run(22, ::solve1)
}