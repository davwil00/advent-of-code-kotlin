package day13

import utils.Coordinate
import utils.readInput
import kotlin.math.min

class PointsOfIncidence(input: String) {

    private val patterns = parseInput(input)

    fun part1(): Int {
        return patterns.sumOf { summarisePatternNotes(it) }
    }

    /**
     * To summarize your pattern notes, add up the number of columns to the left of each vertical line of reflection;
     * to that, also add 100 multiplied by the number of rows above each horizontal line of reflection.
     */
    private fun summarisePatternNotes(pattern: Pattern): Int {
        // try and find a vertical reflection
        // start at the gap between row 0 and 1, check
        val colNumWithReflection = (1 until pattern.width).firstOrNull { colNum ->
            hasReflectionAtColNum(colNum, pattern)
        }

        if (colNumWithReflection != null) {
            return colNumWithReflection
        }

        // try and find a horizontal reflection
        val rowNumWithReflection = (1 until pattern.height).first { rowNum ->
            hasReflectionAtRowNum(rowNum, pattern)
        }

        return rowNumWithReflection * 100
    }

    private fun hasReflectionAtColNum(colNum: Int, pattern: Pattern): Boolean {
        val colsRight = pattern.width - colNum
        val widthToCheck = min(colNum, colsRight)
        val minX = colNum - widthToCheck
        val maxX = colNum + widthToCheck - 1
        val stringsToCheck = (0 until pattern.height).map { y ->
            (minX..maxX).map { x -> pattern.data.getValue(Coordinate(x, y)) }.joinToString("")
        }

        return stringsToCheck.all { it == it.reversed() }
    }

    private fun hasReflectionAtRowNum(rowNum: Int, pattern: Pattern): Boolean {
        val rowsBelow = pattern.height - rowNum
        val heightToCheck = min(rowNum, rowsBelow)
        val minY = rowNum - heightToCheck
        val maxY = rowNum + heightToCheck - 1
        val stringsToCheck = (0 until pattern.width).map { x ->
            (minY .. maxY).map { y -> pattern.data.getValue(Coordinate(x, y)) }.joinToString("")
        }

        return stringsToCheck.all { it == it.reversed() }
    }

    private fun parseInput(input: String): List<Pattern> {
        return input.split("\n\n").map { pattern ->
            val lines = pattern.lines()
            val data = lines.flatMapIndexed { y, row ->
                row.mapIndexed { x, char ->
                    Coordinate(x, y) to char
                }
            }.toMap()
            Pattern(data, lines[0].length, lines.size)
        }
    }
}

data class Pattern(val data: Map<Coordinate, Char>, val width: Int, val height: Int)

fun main() {
    val pointsofincidence = PointsOfIncidence(readInput(13))
    println(pointsofincidence.part1())
    //println(pointsofincidence.part2())
}
