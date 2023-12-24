package day23

import utils.Coordinate
import utils.readGrid
import utils.readInputLines

class ALongWalk(private val input: List<String>) {

    val map = readGrid(input)
    val startingCoordinate = Coordinate(1, 0)
    val destinationCoordinate = Coordinate(input.first().length - 2, input.size - 1)

    fun part1(): Int {
        val possiblePaths = exploreWithSlipperyPaths(startingCoordinate, emptySet())
        return possiblePaths.maxOf { it.size }
    }

    fun part2(): Int {
        val possiblePaths = explore(setOf(ExploredPath(startingCoordinate, emptySet())), emptyList())
        return possiblePaths.maxOf { it.size }
    }

    private fun printPath(longestPath: Set<Coordinate>) {
        input.indices.forEach { y ->
            (0 until input.first().length).forEach { x ->
                val coordinate = Coordinate(x, y)
                if (coordinate in longestPath) {
                    print("O")
                } else {
                    print(map[coordinate])
                }
            }
            println()
        }
    }

    private fun exploreWithSlipperyPaths(
        currentCoordinate: Coordinate,
        tilesVisited: Set<Coordinate>
    ): List<Set<Coordinate>> {
        if (currentCoordinate == destinationCoordinate) {
            return listOf(tilesVisited + currentCoordinate)
        }
        val currentTile = map[currentCoordinate]
        val nextTiles = when (currentTile) {
            ">" -> sequenceOf(currentCoordinate + Coordinate(1, 0))
            "<" -> sequenceOf(currentCoordinate + Coordinate(-1, 0))
            "^" -> sequenceOf(currentCoordinate + Coordinate(0, -1))
            "v" -> sequenceOf(currentCoordinate + Coordinate(0, 1))
            else -> currentCoordinate
                .getAdjacentCoordinates()
                .filter { it in map && map[it] != "#" }
        }.filterNot { it in tilesVisited }
            .toList()

        if (nextTiles.isEmpty()) {
            return emptyList()
        }

        return nextTiles.flatMap { exploreWithSlipperyPaths(it, tilesVisited + it) }
    }

    private fun explore(currentCoordinate: Coordinate, tilesVisited: Set<Coordinate>): List<Set<Coordinate>> {
        if (currentCoordinate == destinationCoordinate) {
            return listOf(tilesVisited + currentCoordinate)
        }
        val nextTiles = currentCoordinate
            .getAdjacentCoordinates()
            .filter { it in map && map[it] != "#" }
            .filterNot { it in tilesVisited }
            .toList()

        if (nextTiles.isEmpty()) {
            return emptyList()
        }

        return nextTiles.flatMap { explore(it, tilesVisited + it) }
    }

    private tailrec fun explore(
        pathsToExplore: Set<ExploredPath>,
        pathsExplored: List<ExploredPath>
    ): List<Set<Coordinate>> {
        return if (pathsToExplore.isEmpty()) {
            pathsExplored.map { it.tilesVisited }
        }
        else {
            val currentPath = pathsToExplore.first()
            if (currentPath.coordinate == destinationCoordinate) {
                explore(pathsToExplore - currentPath, pathsExplored + currentPath)
            } else {
                val nextPaths = currentPath.coordinate
                    .getAdjacentCoordinates()
                    .filter { it in map && map[it] != "#" }
                    .filterNot { it in currentPath.tilesVisited }
                    .map { ExploredPath(it, currentPath.tilesVisited + currentPath.coordinate) }
                    .toList()

                if (nextPaths.isEmpty()) {
                    explore(pathsToExplore - currentPath, pathsExplored)
                } else {
                    explore(pathsToExplore - currentPath + nextPaths, pathsExplored)
                }
            }
        }
    }

    data class ExploredPath(val coordinate: Coordinate, val tilesVisited: Set<Coordinate>)
}

fun main() {
    val aLongWalk = ALongWalk(readInputLines(23))
//    println(aLongWalk.part1())
    println(aLongWalk.part2())
}
