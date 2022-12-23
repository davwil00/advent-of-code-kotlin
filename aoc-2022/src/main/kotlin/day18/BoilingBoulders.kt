package day18

import utils.Coordinate3D
import utils.readInputLines

class BoilingBoulders(input: List<String>) {
    private val lavaBlocks = input.map { coordinate ->
        val (x, y, z) = coordinate.split(",").map { it.toInt() }
        Coordinate3D(x, y, z)
    }

    fun part1(): Int {
        return lavaBlocks.sumOf { countExposedFaces(it) }
    }

    private fun Coordinate3D.getFaceCoordinates() = listOf(
            Coordinate3D(0, 0, 1),
            Coordinate3D(0, 0, -1),
            Coordinate3D(0, 1, 0),
            Coordinate3D(0, -1, 0),
            Coordinate3D(1, 0, 0),
            Coordinate3D(-1, 0, 0),
        ).map { this + it }

    private fun countExposedFaces(lavaBlock: Coordinate3D): Int {
        // Block has 6 exposed faces
        val coveredFaces = lavaBlock.getFaceCoordinates().count {
            lavaBlocks.contains(it)
        }

        return 6 - coveredFaces
    }
}

fun main() {
    val boilingBoulders = BoilingBoulders(readInputLines(18))
    println(boilingBoulders.part1())
    //println(boilingBoulders.part2())
}
