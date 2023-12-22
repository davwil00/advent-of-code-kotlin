package day16

import utils.Coordinate
import utils.readInputLines

class TheFloorWillBeLava(private val input: List<String>) {

    private val floorMap = parseInput(input)

    fun part1(): Int {
        return explore().map { it.coordinate }.toSet().size
    }

    fun part2(): Int {
        val width = input.first().length
        val height = input.size

        val topRow = (0 until width).map { Path(Coordinate(it, 0), Direction.DOWN) }
        val bottomRow = (0 until width).map { Path(Coordinate(it, height - 1), Direction.UP) }
        val firstCol = (0 until height).map { Path(Coordinate(0, it), Direction.RIGHT) }
        val lastCol = (0 until height).map { Path(Coordinate(width - 1, it), Direction.LEFT) }

        return (topRow + bottomRow + firstCol + lastCol).maxOf { explore(listOf(it)).map { it.coordinate }.toSet().size }
    }

    private tailrec fun explore(
        pathsToExplore: List<Path> = listOf(Path(Coordinate(0, 0), Direction.RIGHT)),
        energisedTiles: Set<Path> = emptySet())
    : Set<Path> {
        if (pathsToExplore.isEmpty()) {
            return energisedTiles
        }
        val newlyEnergisedPaths = mutableSetOf<Path>()
        val newPaths = pathsToExplore.flatMap { currentPath ->
            val currentTile = floorMap[(currentPath.coordinate)]
            if (currentTile != null && !energisedTiles.contains(currentPath)) {
                newlyEnergisedPaths.add(currentPath)
                val newDirections = currentTile.directionAfterTile(currentPath.direction)
                newDirections.map { Path(currentPath.coordinate + it.coordinateTransform, it) }
            } else {
                // we've gone off the map!
                emptyList()
            }
        }
        return explore(newPaths, energisedTiles + newlyEnergisedPaths)
    }

    data class Path(val coordinate: Coordinate, val direction: Direction)

    private fun parseInput(input: List<String>): Map<Coordinate, Tile> {
        return input.flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                Coordinate(x, y) to Tile.fromChar(char)
            }
        }.toMap()
    }

    enum class Tile(val char: Char) {
        EMPTY_SPACE('.'),
        BACKSLASH_MIRROR('\\'),
        FORWARD_SLASH_MIRROR('/'),
        HORIZONTAL_SPLITTER('-'),
        VERTICAL_SPLITTER('|');

        fun directionAfterTile(currentDirection: Direction): List<Direction> {
            return when (this) {
                EMPTY_SPACE -> listOf(currentDirection)
                BACKSLASH_MIRROR -> listOf(when (currentDirection) {
                    Direction.UP -> Direction.LEFT
                    Direction.DOWN -> Direction.RIGHT
                    Direction.LEFT -> Direction.UP
                    Direction.RIGHT -> Direction.DOWN
                })
                FORWARD_SLASH_MIRROR -> listOf(when (currentDirection) {
                    Direction.UP -> Direction.RIGHT
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.DOWN
                    Direction.RIGHT -> Direction.UP
                })
                HORIZONTAL_SPLITTER -> when (currentDirection) {
                    Direction.UP, Direction.DOWN -> listOf(Direction.LEFT, Direction.RIGHT)
                    else -> listOf(currentDirection)
                }
                VERTICAL_SPLITTER -> when(currentDirection) {
                    Direction.LEFT, Direction.RIGHT -> listOf(Direction.UP, Direction.DOWN)
                    else -> listOf(currentDirection)
                }
            }
        }

        companion object {
            fun fromChar(char: Char) = entries.first { it.char == char }
        }
    }

    enum class Direction(val coordinateTransform: Coordinate) {
        UP(Coordinate(0, -1)),
        DOWN(Coordinate(0, 1)),
        LEFT(Coordinate(-1, 0)),
        RIGHT(Coordinate(1, 0))
    }
}

fun main() {
    val theFloorWillBeLava = TheFloorWillBeLava(readInputLines(16))
    println(theFloorWillBeLava.part1())
    println(theFloorWillBeLava.part2())
}
