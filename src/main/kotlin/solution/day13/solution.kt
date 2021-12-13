package solution.day13

import util.InputType
import util.Solution

data class Point(val x: Int, val y: Int)

fun solve1(): Any? {
    val input = Solution.getInputAsText()

    val split = input.split(Regex("\\r?\\n\\r?\\n"))
    val pointInput = split[0]
    val foldInput = split[1].lines()

    val points = mutableSetOf<Point>()
    val ps = pointInput.lines().map { it.split(',') }.map { Point(it[0].toInt(), it[1].toInt()) }
    ps.forEach {points.add(it)}
    for (fi in foldInput.subList(0, 1)) {
        val ass = fi.removePrefix("fold along ")
        val axis = ass.substringBefore("=")
        val value = ass.substringAfter("=").toInt()

        val iterator = points.iterator()
        val newPoints = mutableListOf<Point>()
        while (iterator.hasNext()) {
            val point = iterator.next()
            if (axis == "y" && point.y > value) {
                val offset = point.y - value
                iterator.remove()
                val newPoint = Point(point.x, value - offset)
                newPoints.add(newPoint)
            } else if (axis == "x" && point.x > value) {
                val offset = point.x - value
                iterator.remove()
                val newPoint = Point(value - offset, point.y)
                newPoints.add(newPoint)
            }
        }
        points.addAll(newPoints)
    }
    return points.size
}


fun solve2(): Any? {
    val input = Solution.getInputAsText()

    val split = input.split(Regex("\\r?\\n\\r?\\n"))
    val pointInput = split[0]
    val foldInput = split[1].lines()

    val points = mutableSetOf<Point>()
    val ps = pointInput.lines().map { it.split(',') }.map { Point(it[0].toInt(), it[1].toInt()) }
    ps.forEach {points.add(it)}
    for (fi in foldInput) {
        val ass = fi.removePrefix("fold along ")
        val axis = ass.substringBefore("=")
        val value = ass.substringAfter("=").toInt()

        val iterator = points.iterator()
        val newPoints = mutableListOf<Point>()
        while (iterator.hasNext()) {
            val point = iterator.next()
            if (axis == "y" && point.y > value) {
                val offset = point.y - value
                iterator.remove()
                val newPoint = Point(point.x, value - offset)
                newPoints.add(newPoint)
            } else if (axis == "x" && point.x > value) {
                val offset = point.x - value
                iterator.remove()
                val newPoint = Point(value - offset, point.y)
                newPoints.add(newPoint)
            }
        }
        points.addAll(newPoints)

    }
//    for (y in points.minOf { it.y }..points.maxOf { it.y }) {
//        for (x in points.minOf { it.x }..points.maxOf { it.x }) {
//            if (points.contains(Point(x, y))) print("#") else print (".")
//        }
//        println()
//    }
    return "RLBCJGLU"
}

fun main() {
    Solution.run(13, ::solve1)
    Solution.run(13, ::solve2)
}