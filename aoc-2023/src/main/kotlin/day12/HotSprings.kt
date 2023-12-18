package day12

import utils.readInputLines
import kotlin.math.pow

class HotSprings(input: List<String>) {

    private val springRecords = SpringRecord.fromInput(input)

    fun part1(): Int {
        return springRecords.sumOf { it.findPossibleCombinations() }
    }

    data class SpringRecord(val springStates: String, val groups: List<Int>) {
        private val groupRegex = Regex(groups.joinToString("\\.+", "^\\.*", "\\.*") { num -> "#{$num}" })

        fun findPossibleCombinations(): Int {
            val unknownStateLocations = springStates.mapIndexedNotNull { idx, char -> if (char == '?') idx else null }
            val possibleStates = (0 until (2.0.pow(unknownStateLocations.size).toInt())).map {
                val optionToTry = Integer.toBinaryString(it).padStart(unknownStateLocations.size, '0')
                springStates.mapIndexed { idx, char ->
                    if (idx in unknownStateLocations) {
                        if (optionToTry[unknownStateLocations.indexOf(idx)] == '1') '#' else '.'
                    } else {
                        char
                    }
                }.joinToString("")
            }
            .filter { groupRegex.matches(it) }

            return possibleStates.size
        }

        companion object {
            fun fromInput(input: List<String>): List<SpringRecord> {
                return input.map { line ->
                    val (states, groups) = line.split(" ")
                    val groupInts = groups.split(",").map { it.toInt() }
                    SpringRecord(states, groupInts)
                }
            }
        }
    }

    enum class SpringState(value: Char) {
        OPERATIONAL('.'),
        BROKEN('#'),
        UNKNOWN('?');
    }
}

fun main() {
    val hotsprings = HotSprings(readInputLines(12))
    println(hotsprings.part1())
    //println(hotsprings.part2())
}
