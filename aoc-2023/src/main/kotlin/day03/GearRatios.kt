package day03

import utils.Coordinate
import utils.isDigits
import utils.readGrid
import utils.readInputLines

class GearRatios(input: List<String>) {

    private val schematic = readGrid(input)

    fun part1(): Int {
        return schematic
            .filter { (_, value) -> value.isSymbol() }
            .flatMap { findNumberAt(it.key) }
            .sum()
    }

    fun part2(): Int {
        return schematic
            .filter { (_, value) -> value.isSymbol() }
            .map { findNumberAt(it.key) }
            .filter { it.isGear() }
            .sumOf { (g1, g2) -> g1 * g2 }
    }

    private fun findNumberAt(coordinate: Coordinate): List<Int> {
        val surroundingCoordinates = coordinate.getAdjacentCoordinatesIncludingDiagonals()
            .filter { schematic[it]?.isDigits() ?: false }
        val matchLocationsAndValues = surroundingCoordinates.map { surroundingCoordinate ->
            val (x, y) = surroundingCoordinate
            val value = schematic.getValue(surroundingCoordinate)
            val leftValue1 = schematic[Coordinate(x - 1, y)]
            val leftValue2 = schematic[Coordinate(x - 2, y)]
            val rightValue1 = schematic[Coordinate(x + 1, y)]
            val rightValue2 = schematic[Coordinate(x + 2, y)]
            when {
                !leftValue1.isDigits() && !rightValue1.isDigits() -> Pair(Coordinate(x, y), value.toInt())
                leftValue1.isDigits() && leftValue2.isDigits() -> Pair(Coordinate(x - 2, y), "$leftValue2$leftValue1$value".toInt())
                rightValue1.isDigits() && rightValue2.isDigits() -> Pair(Coordinate(x, y), "$value$rightValue1$rightValue2".toInt())
                leftValue1.isDigits() && rightValue1.isDigits() -> Pair(Coordinate(x - 1, y), "$leftValue1$value$rightValue1".toInt())
                leftValue1.isDigits() && !rightValue1.isDigits() -> Pair(Coordinate(x - 1, y), "$leftValue1$value".toInt())
                !leftValue1.isDigits() && rightValue1.isDigits() -> Pair(Coordinate(x, y), "$value$rightValue1".toInt())
                else -> throw IllegalStateException("Not possible")
            }
        }.toSet()
            .filter { it.first.x >= 0 }
        return matchLocationsAndValues.map { it.second }
    }

    private fun String.isSymbol() = !this.isDigits() && this != "."
    private fun List<Int>.isGear() = size == 2
}

fun main() {
    val gearRatios = GearRatios(readInputLines(3))
    println(gearRatios.part1())
    println(gearRatios.part2())
}
