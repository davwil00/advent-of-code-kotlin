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

fun readGrid(input: List<String>) = input.flatMapIndexed { rowIdx, line ->
    line.splitToString()
        .withIndex()
        .associate { (colIdx, value) ->
            Pair(Coordinate(colIdx, rowIdx), value)
        }.entries
}.associate { it.key to it.value }

fun printBlock() {
    print('\u2593')
}

fun <T> generateCombinations(list: List<T>): List<Pair<T, T>> {
    return list.flatMapIndexed { i, first ->
        list.subList(i + 1, list.size).map { second -> Pair(first, second) }
    }
}
