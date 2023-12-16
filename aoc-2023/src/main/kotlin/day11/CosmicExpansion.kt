package day11

import utils.Coordinate
import utils.generateCombinations
import utils.readInputLines
import kotlin.math.abs

class CosmicExpansion(input: List<String>) {

    private val universe = Universe(input)

    fun part1(): Long {
        val galaxies = universe.findGalaxiesInExpandedUniverse(2)
        return findSumOfDistanceBetweenGalaxies(galaxies)
    }

    fun part2(scale: Int = 1000000): Long {
        val galaxies = universe.findGalaxiesInExpandedUniverse(scale)
        return findSumOfDistanceBetweenGalaxies(galaxies)
    }

    private fun findSumOfDistanceBetweenGalaxies(galaxies: List<Coordinate>) =
        generateCombinations(galaxies).sumOf { (galaxy1, galaxy2) ->
            val coordinateDiff = galaxy1 - galaxy2
            abs(coordinateDiff.x) + abs(coordinateDiff.y).toLong()
        }

    data class Universe(private val input: List<String>) {

        fun findGalaxiesInExpandedUniverse(scale: Int): List<Coordinate> {
            val colsToExpand = getColsToExpand()
            val rowsToExpand = getRowsToExpand()

            return findGalaxies().map { galaxy ->
                val newX = galaxy.x + colsToExpand.count { it < galaxy.x } * (scale - 1)
                val newY = galaxy.y + rowsToExpand.count { it < galaxy.y } * (scale - 1)
                Coordinate(newX, newY)
            }
        }

        private fun findGalaxies() = input.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char ->
                if (char == '#') Coordinate(x, y) else null
            }
        }

        private fun getRowsToExpand() = input.mapIndexedNotNull { idx, row ->
            if (row.contains("#")) {
                null
            } else {
                idx
            }
        }

        private fun getColsToExpand() = (0 until input.first().length).mapNotNull { col ->
            if (input.map { row -> row[col] }.any { it == '#' }) {
                null
            } else {
                col
            }
        }
    }
}

fun main() {
    val cosmicExpansion = CosmicExpansion(readInputLines(11))
    println(cosmicExpansion.part1())
    println(cosmicExpansion.part2())
}
