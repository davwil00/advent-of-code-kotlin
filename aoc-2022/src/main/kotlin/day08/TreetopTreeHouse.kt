package day08

import utils.Coordinate
import utils.asInt
import utils.readInputLines
import utils.takeWhileInclusive

class TreetopTreeHouse(input: List<String>) {

    private val treeMap = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            Coordinate(x, y) to col.asInt()
        }
    }.toMap()
    private val mapWidth = input.first().length
    private val mapHeight = input.size

    fun part1() = treeMap.keys.count { coordinate ->
        coordinate.x == 0 || coordinate.x == mapWidth - 1 ||
        coordinate.y == 0 || coordinate.y == mapHeight - 1 ||
        isVisible(coordinate)
    }

    private fun isVisible(coordinate: Coordinate) =
        visibleLeft(coordinate) ||
        visibleRight(coordinate) ||
        visibleUp(coordinate) ||
        visibleDown(coordinate)

    private fun isVisibleFromX(from: Int, to: Int, coordinate: Coordinate) =
        (from until to).all { x ->
            treeMap[Coordinate(x, coordinate.y)]!! < treeMap[coordinate]!!
        }

    private fun isVisibleFromY(from: Int, to: Int, coordinate: Coordinate) =
        (from until to).all { y ->
            treeMap[Coordinate(coordinate.x, y)]!! < treeMap[coordinate]!!
        }

    private fun visibleLeft(coordinate: Coordinate) = isVisibleFromX(0, coordinate.x, coordinate)
    private fun visibleRight(coordinate: Coordinate) = isVisibleFromX(coordinate.x + 1, mapWidth, coordinate)
    private fun visibleUp(coordinate: Coordinate) = isVisibleFromY(0, coordinate.y, coordinate)
    private fun visibleDown(coordinate: Coordinate) = isVisibleFromY(coordinate.y + 1, mapHeight, coordinate)

    fun part2() = treeMap.entries.maxOfOrNull { (coordinate, height) ->
        scenicScore(coordinate, height)
    }

    fun scenicScore(coordinate: Coordinate, height: Int): Long {
        val up = countUp(coordinate, height).toLong()
        val left = countLeft(coordinate, height).toLong()
        val right = countRight(coordinate, height).toLong()
        val down = countDown(coordinate, height).toLong()
        return up * left * down * right
    }

    private fun countUp(coordinate: Coordinate, height: Int) =
        (coordinate.y - 1 downTo 0).takeWhileInclusive { y ->
            treeMap[Coordinate(coordinate.x, y)]!! < height
        }.count()

    private fun countLeft(coordinate: Coordinate, height: Int) =
        (coordinate.x - 1 downTo 0).takeWhileInclusive { x ->
            treeMap[Coordinate(x, coordinate.y)]!! < height
        }.count()

    private fun countRight(coordinate: Coordinate, height: Int) =
        (coordinate.x + 1 until mapWidth).takeWhileInclusive { x ->
            treeMap[Coordinate(x, coordinate.y)]!! < height
        }.count()

    private fun countDown(coordinate: Coordinate, height: Int) =
        (coordinate.y + 1 until mapHeight).takeWhileInclusive { y ->
            treeMap[Coordinate(coordinate.x, y)]!! < height
        }.count()
}

fun main() {
    val treetopTreeHouse = TreetopTreeHouse(readInputLines(8))
    println(treetopTreeHouse.part1())
    println(treetopTreeHouse.part2())
}
