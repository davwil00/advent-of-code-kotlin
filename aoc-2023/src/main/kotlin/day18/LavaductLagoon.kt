package day18

import utils.Coordinate
import utils.printBlock
import utils.readInputLines
import kotlin.math.max

class LavaductLagoon(input: List<String>) {

    private val instructions = parseInstructions(input)

    fun part1(): Int {
        val map = createLavaMap()
//
//        val minX = map.minOf { it.x } - 1
//        val maxX = map.maxOf { it.x } + 1
//        val minY = map.minOf { it.y } - 1
//        val maxY = map.maxOf { it.y } + 1
        return floodFill(map, Coordinate(map.first()))

//        val nonMapCount = traverseMap(Pair(Coordinate(minX, minY), Coordinate(maxX, maxY)), setOf(Coordinate(minX, minY)), map)
//        return ((maxY - minY) * (maxX - minX)) - nonMapCount
//
//        var totalDug = 0
//// left block must not be in the map and must be a stopper block at some point?
//        (minY..maxY).forEach { y ->
//            var digging = false
//            (minX..maxX).forEach { x ->
//                if (Coordinate(x, y) in map) {
//                    totalDug++
//                    printBlock()
//                    val direction = map.getValue(Coordinate(x, y))
//                    if (direction == Direction.U) {
//                        digging = true
//                    } else if (direction == Direction.D) {
//                        digging = false
//                    }
//                } else if (digging) {
//                    print("#")
//                    totalDug++
//                } else {
//                    print(".")
//                }
//            }
//            println()
//        }
//
//        return to
    }

    /**
     * Start by filling the current scanline from one end to the other
     * While filling the current scanline, test for the ends of spans above and below
     * For each new free span, plant a seed
     * Repeat until there are no more seeds
     */
    private fun floodFill(map: Set<Coordinate>, coordinatesToVisit: Set<Coordinate>, coordinatesVisited: Set<Coordinate>): Int {
        if (coordinatesToVisit.isEmpty()) {
            return coordinatesVisited.size
        }

        val currentCoordinate = coordinatesToVisit.first()
        // scan left and right until find a thing in the map
        // find the leftmost thing in the map
        val minX = map.maxBy { it.x < currentCoordinate.x }.x
        val maxX = map.minBy { it.x > currentCoordinate.x }.x
        (minX .. maxX).map {
            Coordinate(it, currentCoordinate.y)
        }
        floodFill()
    }

    private fun createLavaMapWithDirection(): Map<Coordinate, Direction> {
        val map = instructions.fold(Pair(setOf<Pair<Coordinate, Direction>>(), Coordinate(0, 0))) { acc, curr ->
            val newBorders = (0..curr.amount).map {
                (acc.second + curr.direction.coordinateDiff * it) to curr.direction
            }
            Pair(acc.first + newBorders, newBorders.last().first)
        }.first.associate { it }
        return map
    }

    private fun createLavaMap(): Set<Coordinate> {
        return instructions.fold(listOf(Coordinate(0, 0))) { acc, curr ->
            acc + (0..curr.amount).map {
                (acc.last() + curr.direction.coordinateDiff * it)
            }
        }.toSet()
    }

    private fun parseInstructions(input: List<String>): List<Instruction> {
        return input.map { line ->
            val (directionChar, amount, colour) = line.split(" ")
            Instruction(Direction.valueOf(directionChar), amount.toInt(), colour.substring(1, colour.length - 2))
        }
    }

    data class Instruction(val direction: Direction, val amount: Int, val colour: String)

    enum class Direction(val coordinateDiff: Coordinate) {
        U(Coordinate(0, -1)),
        D(Coordinate(0, 1)),
        L(Coordinate(-1, 0)),
        R(Coordinate(1, 0));
    }
}

fun main() {
    val lavaductLagoon = LavaductLagoon(readInputLines(18))
    println(lavaductLagoon.part1()) // 47767 too high
    //println(lavaductlagoon.part2())
}
