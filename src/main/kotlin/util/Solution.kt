package util

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess
import kotlin.text.StringBuilder

object Solution {
    private var lastRegisteredDay: Int? = null

    fun registerForDay(day: Int, func: () -> Unit) {
        lastRegisteredDay = day
        printGreeting(day)

        print("example output: ")
        func()

        print("real output: ")
        func()
        println("")
    }

    private val inputFileIterator = generateSequence(0) { it + 1 }.map {
        if (it % 2 == 0) {
            "day${lastRegisteredDay!!}_sample.txt"
        } else {
            "day${lastRegisteredDay!!}_real.txt"
        }
    }.iterator()

    fun getInputAsText(): String {
        if (!inputFileIterator.hasNext()) {
            throw IllegalStateException("All inputs for this day where already consumed")
        }
        val fileName = inputFileIterator.next()
        var inputRes = getResource("./inputs/$fileName")

        if (inputRes == null) {
            var inputFolder = getResource("./inputs/")
            if (inputFolder == null) {
                val source = Paths.get(getResource("/")!!.toURI())
                Files.createDirectory(
                    Paths.get(
                        source.toAbsolutePath().toString().replace("build/resources/main", "src/main/resources")
                            .replace("build\\resources\\main", "src\\main\\resources")
                    )
                )
                Files.createDirectory(Paths.get(source.toAbsolutePath().toString()))
                inputFolder = getResource("./inputs/")!!
            }
            val source = Paths.get(inputFolder.toURI())
            Files.createFile(
                Paths.get(
                    source.toAbsolutePath().toString().replace("build/resources/main", "src/main/resources")
                        .replace("build\\resources\\main", "src\\main\\resources"), fileName
                )
            )
            Files.createFile(Paths.get(source.toAbsolutePath().toString(), fileName))
            inputRes = getResource("./inputs/$fileName")!!
        }
        val text = inputRes.readText()
        if (text.isEmpty()) {
            println("${ConsoleColors.RED}fill $fileName${ConsoleColors.RESET}")
            exitProcess(0)
        }

        return text.trim()
    }

    private val greetedDay = mutableSetOf<Int>()
    private fun printGreeting(day: Int) {
        if (greetedDay.add(day)) {
            Thread.sleep(10)
            val sb = StringBuilder()

            val inset = "Day $day"
            val header = "*".repeat(22 + inset.length).makeBlinky()
            sb.appendLine(header)
            sb.append("*".makeBlinky())
            sb.append("${" ".repeat(10)}$inset${" ".repeat(10)}")
            sb.appendLine("*".makeBlinky())
            sb.appendLine(header)

            println(sb.toString())
        }
    }
}

object ConsoleColors {
    const val BLACK = "\u001b[0;30m" // BLACK
    const val RED = "\u001b[0;31m" // RED
    const val GREEN = "\u001b[0;32m" // GREEN
    const val YELLOW = "\u001b[0;33m" // YELLOW
    const val BLUE = "\u001b[0;34m" // BLUE
    const val PURPLE = "\u001b[0;35m" // PURPLE
    const val CYAN = "\u001b[0;36m" // CYAN
    const val WHITE = "\u001b[0;37m" // WHITE

    const val RESET = "\u001b[0m" // Text Reset

    val blinkyColors = listOf(RED, GREEN, YELLOW, BLUE, PURPLE, CYAN)

    fun makeStringBlink(s: String) {

    }
}

fun String.makeBlinky(): String {
    val sb = StringBuilder()
    this.toList().forEach {
        sb.append(ConsoleColors.blinkyColors.random() + it + ConsoleColors.RESET)
    }
    return sb.toString()
}