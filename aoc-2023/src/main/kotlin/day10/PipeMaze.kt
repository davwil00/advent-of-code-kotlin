package day10

import utils.Coordinate
import utils.readGrid
import utils.readInputLines
import java.lang.IllegalStateException

class PipeMaze(input: List<String>) {
    private lateinit var startLocation: Coordinate
    private val pipeGrid = createPipeGrid(input)

    private fun createPipeGrid(input: List<String>): Map<Coordinate, Pipe> {
        val grid = readGrid(input)
            .mapValues { (coordinate, value) ->
                if (value == "S") {
                    startLocation = coordinate
                }
                value
            }

        val pipesAdjacentToStart = sequenceOf(
            startLocation + Direction.NORTH.coordinate,
            startLocation + Direction.EAST.coordinate,
            startLocation + Direction.SOUTH.coordinate,
            startLocation + Direction.WEST.coordinate,
        )
            .map { grid[it] }
            .map { if (it == null) null else Pipe.fromValue(it) }
            .toList()
        val startPipe = determineStartType(pipesAdjacentToStart)

        return grid.mapValues { (_, value) -> if (value == "S") startPipe else Pipe.fromValue(value) }
    }

    private fun determineStartType(adjacentValues: List<Pipe?>): Pipe {
        val (north, east, south, west) = adjacentValues
        return when {
            north?.isAccessibleFrom(Direction.SOUTH) ?: false -> {
                when {
                    east?.isAccessibleFrom(Direction.WEST) ?: false -> Pipe.NE
                    south?.isAccessibleFrom(Direction.NORTH) ?: false -> Pipe.NS
                    west?.isAccessibleFrom(Direction.EAST) ?: false -> Pipe.NW
                    else -> throw IllegalStateException()
                }
            }

            south?.isAccessibleFrom(Direction.NORTH) ?: false -> {
                when {
                    east?.isAccessibleFrom(Direction.WEST) ?: false -> Pipe.SE
                    west?.isAccessibleFrom(Direction.EAST) ?: false -> Pipe.SW
                    else -> throw IllegalStateException()
                }
            }

            else -> Pipe.EW
        }
    }

    fun part1(): Int {
        val loopTiles = traverseGrid(startLocation)
        return loopTiles.size / 2
    }

    private tailrec fun traverseGrid(
        currentLocation: Coordinate,
        previousLocation: Coordinate? = null,
        loop: Set<Coordinate> = emptySet()
    ): Set<Coordinate> {
        val directionToTravel = currentLocation.getAdjacentCoordinates()
            .filter { it != previousLocation }
            .map { Direction.fromCoordinate(it - currentLocation) }
            .first { pipeGrid.getValue(currentLocation).isAccessibleFrom(it) }

        val nextLocation = currentLocation + directionToTravel.coordinate
        if (nextLocation == startLocation) {
            return loop + currentLocation
        }

        return traverseGrid(nextLocation, currentLocation, loop + currentLocation)
    }

    enum class Direction(val coordinate: Coordinate) {
        NORTH(Coordinate(0, -1)),
        SOUTH(Coordinate(0, 1)),
        EAST(Coordinate(1, 0)),
        WEST(Coordinate(-1, 0));

        companion object {
            fun fromCoordinate(coordinate: Coordinate) = entries.first { it.coordinate == coordinate }
        }
    }

    enum class Pipe(private val value: String) {
        NS("|"),
        EW("-"),
        NE("L"),
        NW("J"),
        SW("7"),
        SE("F"),
        GROUND(".");

        fun isAccessibleFrom(direction: Direction): Boolean = when (direction) {
            Direction.NORTH -> this == NS || this == NE || this == NW
            Direction.EAST -> this == EW || this == NE || this == SE
            Direction.SOUTH -> this == NS || this == SW || this == SE
            Direction.WEST -> this == EW || this == NW || this == SW
        }

        companion object {
            fun fromValue(value: String) = entries.first { it.value == value }
        }
    }

}

fun main() {
    val pipeMaze = PipeMaze(readInputLines(10))
    println(pipeMaze.part1())
    //println(pipemaze.part2())
}
