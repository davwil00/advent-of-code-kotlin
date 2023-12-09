package day09

import utils.readInputLines

class MirageMaintenance(private val input: List<String>) {

    fun part1(): Int {
        return input.sumOf { line ->
            val sequence = line.split(" ").map { it.toInt() }
            sequence.last() + calculateNextValueIncrement(sequence)
        }
    }

    fun part2(): Int {
        return input.sumOf { line ->
            val sequence = line.split(" ").map { it.toInt() }
            sequence.first() - calculatePreviousValueIncrement(sequence)
        }
    }

    private fun calculateNextValueIncrement(sequence: List<Int>): Int {
        if (sequence.all { it == 0 }) {
            return 0
        }

        val differences = sequence.zipWithNext().map { (first, second) -> second - first }
        return differences.last() + calculateNextValueIncrement(differences)
    }

    private fun calculatePreviousValueIncrement(sequence: List<Int>): Int {
        if (sequence.all { it == 0 }) {
            return 0
        }

        val differences = sequence.zipWithNext().map { (first, second) -> second - first }
        return differences.first() - calculatePreviousValueIncrement(differences)
    }
}

fun main() {
    val mirageMaintenance = MirageMaintenance(readInputLines(9))
    println(mirageMaintenance.part1())
    println(mirageMaintenance.part2())
}
