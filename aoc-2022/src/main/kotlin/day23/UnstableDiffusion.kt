package day23

import org.slf4j.LoggerFactory
import utils.Coordinate
import utils.CoordinateTransform
import utils.printGrid
import utils.range
import utils.readInputLines

class UnstableDiffusion(input: List<String>) {
    private val elvesStartingCoordinates = input.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, char ->
            if (char == '#') {
                Coordinate(x, y)
            } else {
                null
            }
        }
    }.toSet()

    fun part1(): Int {
        var startPositions: Set<Coordinate>?
        var newPositions = elvesStartingCoordinates
        var roundNumber = 0
        do {
            startPositions = newPositions
            newPositions = doRound(startPositions, roundNumber++)
            logger.debug("---- Round $roundNumber ----")
            newPositions.printGrid(-3, 11)
        } while (roundNumber < 10)
        val range = newPositions.range()
        return range.second.sumOf { y ->
            range.first.count { x ->
                Coordinate(x, y) !in newPositions
            }
        }
    }

    fun part2() : Int {
        var startPositions: Set<Coordinate>?
        var newPositions = elvesStartingCoordinates
        var roundNumber = 0
        do {
            startPositions = newPositions
            newPositions = doRound(startPositions, roundNumber++)
        } while ( startPositions != newPositions)

        return roundNumber
    }

    class PositionsToCheck(private val directions: List<CoordinateTransform>, val direction: CoordinateTransform){
        fun checkPositions(elf: Coordinate, map: Set<Coordinate>) = directions.none { map.contains(it(elf)) }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UnstableDiffusion::class.java)
        private val checkNorth = PositionsToCheck(listOf(Coordinate::N, Coordinate::NE, Coordinate::NW), Coordinate::N)
        private val checkSouth = PositionsToCheck(listOf(Coordinate::S, Coordinate::SE, Coordinate::SW), Coordinate::S)
        private val checkWest = PositionsToCheck(listOf(Coordinate::W, Coordinate::NW, Coordinate::SW), Coordinate::W)
        private val checkEast = PositionsToCheck(listOf(Coordinate::E, Coordinate::NE, Coordinate::SE), Coordinate::E)
    }

    private fun getPositionsToCheck(roundNumber: Int) = when (roundNumber % 4) {
            0 -> listOf(checkNorth, checkSouth, checkWest, checkEast)
            1 -> listOf(checkSouth, checkWest, checkEast, checkNorth)
            2 -> listOf(checkWest, checkEast, checkNorth, checkSouth)
            else -> listOf(checkEast, checkNorth, checkSouth, checkWest)
        }

    private fun doRound(map: Set<Coordinate>, roundNumber: Int): Set<Coordinate> {
        val positionsToCheck = getPositionsToCheck(roundNumber)
        val proposedMoves = map.map { elf ->
            when {
                elf.getAdjacentCoordinatesIncludingDiagonals(Int.MIN_VALUE, Int.MIN_VALUE).none { it in map } -> elf to elf
                positionsToCheck[0].checkPositions(elf, map) -> elf to positionsToCheck[0].direction(elf)
                positionsToCheck[1].checkPositions(elf, map) -> elf to positionsToCheck[1].direction(elf)
                positionsToCheck[2].checkPositions(elf, map) -> elf to positionsToCheck[2].direction(elf)
                positionsToCheck[3].checkPositions(elf, map) -> elf to positionsToCheck[3].direction(elf)
                else -> elf to elf
            }
        }
        val uniqueMoves = mutableSetOf<Coordinate>()
        val duplicateMoves = proposedMoves
            .filter { move ->
                !uniqueMoves.add(move.second)
            }
            .map { it.second }
            .toSet()
        return proposedMoves.map { move ->
            if (move.second in duplicateMoves) {
                move.first
            } else {
                move.second
            }
        }.toSet()
    }
}

fun main() {
    val unstableDiffusion = UnstableDiffusion(readInputLines(23))
    println(unstableDiffusion.part1())
    println(unstableDiffusion.part2())
}
