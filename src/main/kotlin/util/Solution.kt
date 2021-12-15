package util

import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Time
import java.util.*
import kotlin.io.path.Path
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

const val inputFolderName = "inputs"

private fun getSampleInputFileName(day: Int) = "day${day}_sample.txt"
private fun getSampleExpectedFileName(day: Int) = "day${day}_expected.txt"
private fun getRealInputFileName(day: Int) = "day${day}_real.txt"

enum class InputType {
    REAL,
    SAMPLE
}

object Solution {
    private var currentInputFile: String? = null
    private var levelCount: Int = 1
    private val greetedDay = mutableSetOf<Int>()

    fun run(day: Int, func: () -> Any?, debug: InputType? = null) {
        ensureInputsAreCreated(day)
        printGreeting(day)

        if (debug == InputType.SAMPLE) {
            currentInputFile = getSampleInputFileName(day)
            func()
            return
        }

        if (debug == InputType.REAL) {
            currentInputFile = getRealInputFileName(day)
            func()
            return
        }
        val solution = getSolutions(day).getOrNull(levelCount - 1)

        var res1: Any?
        val res1Time = measureTimeMillis {
            currentInputFile = getSampleInputFileName(day)
            res1 = func()
        }

        var res2: Any?
        val res2Time = measureTimeMillis {
            currentInputFile = getRealInputFileName(day)
            res2 = func()
        }

        var checkResult = "- NO EXPECTED OUTPUT FOUND"
        var error = false
        var success = false
        if (solution != null) {
            checkResult =
                if (solution.toString() == res1.toString()) "${ConsoleColors.GREEN + ConsoleColors.BLACK_BACKGROUND_BRIGHT}✅".also {
                    success = true
                } else "❌".also { error = true }
        }

        println("${ConsoleColors.BLACK_BACKGROUND_BRIGHT} Level $levelCount $checkResult (${res1Time}ms, ${res2Time}ms)")
        println("${ConsoleColors.RESET} sample output: ${if (error) "${ConsoleColors.RED}$res1 (expected $solution)" else "$res1"}${ConsoleColors.RESET}")

        println(" real output:   ${if (success) ConsoleColors.PURPLE else ConsoleColors.RESET}$res2${ConsoleColors.RESET}")
        println("")

        levelCount++
    }

    fun getInputAsText(): String {
        return getResourceAsText("${inputFolderName}/${currentInputFile}").trim()
    }

    private fun getSolutions(day: Int): List<String> {
        return getResource("$inputFolderName/${getSampleExpectedFileName(day)}")!!.readText().trim().lines()
    }

    private fun ensureInputsAreCreated(day: Int) {
        val inputFolder = getInputFolder()

        val inputs = listOf(
            getSampleInputFileName(day),
            getSampleExpectedFileName(day),
            getRealInputFileName(day)
        )

        val inputResources = inputs.map { getResource("$inputFolderName/$it") }

        if (inputResources.all { it != null && it.readText().isNotEmpty() }) {
            return
        }

        inputs.forEach {
            val res = getResource("$inputFolderName/$it")

            if (res == null) {
                Files.createFile(Path("${inputFolder.path.trimStart('/')}/$it"))
            }

            if (res == null || res.readText().isEmpty()) {
                println("${ConsoleColors.GREEN}fill ${inputFolder.path.trimStart('/')}/$it${ConsoleColors.RESET}")
            }
        }

        exitProcess(0)
    }

    private fun getInputFolder(): URL {
        val inputFolder = getResource(inputFolderName)
        if (inputFolder != null) {
            return inputFolder
        }

        val outputResources = Paths.get(getResource("/")!!.toURI()).toAbsolutePath().toString()
        Files.createDirectory(Paths.get(outputResources, inputFolderName))
        return getResource(inputFolderName)!!
    }

    private fun printGreeting(day: Int) {
        if (greetedDay.add(day)) {
            val inputFolder = getInputFolder()

            val inputs = listOf(
                getSampleInputFileName(day),
                getSampleExpectedFileName(day),
                getRealInputFileName(day)
            )

            inputs.forEach {
                println("${ConsoleColors.GREEN}${inputFolder.path.trimStart('/')}/$it${ConsoleColors.RESET}")
            }
            println("")

            Thread.sleep(10)
            val sb = StringBuilder()

            val inset = "Day $day"
            val header = "#".repeat(22 + inset.length)
            sb.appendLine(header.makeBlink())
            sb.append("#".makeBlink())
            sb.append("${" ".repeat(10)}$inset${" ".repeat(10)}")
            sb.appendLine("#".makeBlink())
            sb.appendLine(header.makeBlink())

            println(sb.toString())
        }
    }
}

fun String.makeBlink(): String {
    val sb = StringBuilder()
    this.toList().forEach {
        sb.append(ConsoleColors.blinkColors.random() + it + ConsoleColors.RESET)
    }
    return sb.toString()
}