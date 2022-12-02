package utils

import java.io.File

// Set working directory to `$MODULE_WORKING_DIR$` in run config to ensure this loads correctly
fun readInput(day: Int, fileName: String = "input.txt") : String {
    val paddedDay = day.toString().padStart(2, '0')
    return File("src/main/resources/day$paddedDay/$fileName").readText().trim()
}

fun readInputLines(day: Int, fileName: String = "input.txt") : List<String> {
    return readInput(day, fileName).lines()
}

fun readSingleInputLineOfIntsFromCsv(day: Int, fileName: String = "input.txt"): List<Int> {
    return readInput(day, fileName).split(",").map { it.toInt() }
}
