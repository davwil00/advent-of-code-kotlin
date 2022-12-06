package day06

import utils.readInput

class TuningTrouble(private val input: String) {

    fun part1() = findUniqueSequence(4)
    fun part2() = findUniqueSequence(14)

    private fun findUniqueSequence(size: Int) = input
        .withIndex()
        .windowed(size)
        .first { idxVal -> idxVal.map{ it.value }.toSet().size == size }
        .last().index + 1
}

fun main() {
    val tuningTrouble = TuningTrouble(readInput(6))
    println(tuningTrouble.part1())
    println(tuningTrouble.part2())
}
