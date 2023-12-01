package day01

import utils.asInt
import utils.readInputLines

class Trebuchet() {
    fun extractFirstAndLastNumbers(input: String): Long {
        val digits = input.filter { it.isDigit() }
        return "${digits.first()}${digits.last()}".toLong()
    }

    private val digitMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    private fun Map<String, Int>.containsAnyKeyMatching(stringToCheck: String) = entries.firstOrNull { stringToCheck.contains(it.key) }

    tailrec fun findFirstDigitOrWord(input: String, index: Int = 0): Int {
        if (input[index].isDigit()) {
            return input[index].asInt()
        }

        val stringToCheck = input.substring(0, index + 1)

        digitMap.containsAnyKeyMatching(stringToCheck)?.let {
            return it.value
        }

        return findFirstDigitOrWord(input, index + 1)
    }

    tailrec fun findLastDigitOrWord(input: String, index: Int = input.length - 1): Int {
        if (input[index].isDigit()) {
            return input[index].asInt()
        }

        val stringToCheck = input.substring(index, input.length)

        digitMap.containsAnyKeyMatching(stringToCheck)?.let {
            return it.value
        }

        return findLastDigitOrWord(input, index - 1)
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { extractFirstAndLastNumbers(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { "${findFirstDigitOrWord(it)}${findLastDigitOrWord(it)}".toInt() }
    }
}

fun main() {
    val input = readInputLines(1)
    val trebuchet = Trebuchet()
    println(trebuchet.part1(input))
    println(trebuchet.part2(input))
}
