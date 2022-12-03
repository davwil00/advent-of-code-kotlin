package day03

import utils.readInputLines

class RucksackReorganisation(input: List<String>) {
    val rucksackContents = input.map { rucksack ->
        val compartmentSize = rucksack.length / 2
        Rucksack(rucksack.substring(0, compartmentSize), rucksack.substring(compartmentSize))
    }

    fun part1() =
        rucksackContents
            .map { findOverlappingItems(it) }
            .sumOf { getPriority(it) }

    fun part2() =
        rucksackContents
            .chunked(3)
            .map { (a, b, c) -> findCommonItem(a, b, c) }
            .sumOf{ getPriority(it) }

    fun findOverlappingItems(rucksack: Rucksack) =
        rucksack.compartment1.toSet().intersect(rucksack.compartment2.toSet()).first()

    fun findCommonItem(rucksack1: Rucksack, rucksack2: Rucksack, rucksack3: Rucksack): Char {
        return rucksack1.items().intersect(rucksack2.items()).intersect(rucksack3.items()).first()
    }

    fun getPriority(char: Char): Int = if (char.isLowerCase()) {
            char.code - 96 // a == 97
        } else {
            char.code - 38 // A = 64
        }

    data class Rucksack(val compartment1: String, val compartment2: String) {
        fun items() = (compartment1 + compartment2).toSet()
    }
}

fun main() {
    val rucksackReorganisation = RucksackReorganisation(readInputLines(3))
    println(rucksackReorganisation.part1())
    println(rucksackReorganisation.part2())
}
