package day21

import utils.Coordinate
import utils.readInputLines
import utils.splitToString

class StepCounter(private val input: List<String>, private val stepTarget: Int) {
    private val map = parseInput(input)

    fun part1(): Int {
        val startingY = input.indexOfFirst { it.contains('S') }
        val startingX = input[startingY].indexOf('S')
        val startingCoordinate = Coordinate(startingX, startingY)
        return findPossibleNextSteps(setOf(startingCoordinate))
    }

    private tailrec fun findPossibleNextSteps(currentPossibleSteps: Set<Coordinate>, numberOfStepsTaken: Int = 0): Int {
        if (numberOfStepsTaken == stepTarget) {
            return currentPossibleSteps.size
        }
        val nextPossibleLocations = currentPossibleSteps.flatMap { coordinate ->
            coordinate.getAdjacentCoordinates()
                .filter { map.contains(it) }
        }.toSet()
        return findPossibleNextSteps(nextPossibleLocations, numberOfStepsTaken + 1)
    }

    private fun parseInput(input: List<String>): Set<Coordinate> {
        return input.flatMapIndexed { y, row ->
            row.splitToString().mapIndexedNotNull { x, char ->
                if (char == "." || char == "S") {
                    Coordinate(x, y)
                } else null
            }
        }.toSet()
    }
}

fun main() {
    val stepcounter = StepCounter(readInputLines(21), 64)
    println(stepcounter.part1())
    //println(stepcounter.part2())
}
