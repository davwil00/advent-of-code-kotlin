package day04

import utils.readInputLines
import kotlin.math.pow

class Scratchcards(private val input: List<String>) {
    private val scratchcards = parseInput()

    fun part1(): Int {
        return scratchcards.values.sumOf { it.calculatePoints() }
    }

    fun part2(): Int {
        val cardCount = buildMap {
            (1..scratchcards.size).forEach {
                put(it, 1)
            }
        }.toMutableMap()

        scratchcards.values.forEach { scratchcard ->
            val numberOfMatches = scratchcard.numberOfMatches()
            ((scratchcard.cardNo + 1) .. scratchcard.cardNo + numberOfMatches).forEach {
                cardCount[it] = cardCount.getValue(it) + cardCount.getValue(scratchcard.cardNo)
            }
        }

        return cardCount.values.sum()
    }

    private fun parseInput(): Map<Int, Scratchcard> = input.map { line ->
        val (cardNo, winningNumbers, yourNumbers) = line.split(":", "|")
        Scratchcard(
            cardNo.split(someSpacesRegex)[1].toInt(),
            parseNumberList(winningNumbers),
            parseNumberList(yourNumbers)
        )
    }.associateBy { it.cardNo }

    private fun parseNumberList(numberList: String) = numberList.trim().split(someSpacesRegex).map { it.toInt() }.toSet()

    data class Scratchcard(val cardNo: Int, val winningNumbers: Set<Int>, val yourNumbers: Set<Int>) {
        fun calculatePoints(): Int {
            return if (numberOfMatches() == 0) {
                0
            } else {
                2.0.pow(numberOfMatches() - 1).toInt()
            }
        }

        fun numberOfMatches() = winningNumbers.intersect(yourNumbers).size
    }

    companion object {
        private val someSpacesRegex = Regex(" +")
    }
}

fun main() {
    val scratchcards = Scratchcards(readInputLines(4))
    println(scratchcards.part1())
    println(scratchcards.part2())
}
