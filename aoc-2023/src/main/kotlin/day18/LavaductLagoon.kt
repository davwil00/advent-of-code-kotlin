package day18

import utils.Coordinate
import utils.readInputLines
import kotlin.math.abs

class LavaductLagoon(private val input: List<String>) {

    fun part1(): Int {
        val instructions = input.map { Instruction.fromString(it) }
        return shoelaceFormula(instructions).toInt()
    }

    fun part2(): Long {
        val instructions = input.map { Instruction.fromString(it, true) }
        return shoelaceFormula(instructions).toLong()
    }

    /**
     * Calculate the area with the shoelace formula
     * https://www.themathdoctors.org/polygon-coordinates-and-areas/
     *
     * Add half the perimeter (+1)
     */
    private fun shoelaceFormula(instructions: List<Instruction>): Double {
        // find the coordinates of the corners
        val vertices = instructions.fold(listOf(Coordinate(0, 0))) { acc, curr ->
            acc + (acc.last() + curr.direction.coordinateDiff * curr.amount)
        }.toSet()

        // S1 = x1*y2 + x2*y3 + x3*y1
        val s1 =  vertices.windowed(2, partialWindows = true).sumOf { pair ->
            if (pair.size == 1) {
                (pair.first().x.toLong() * vertices.first().y.toLong())
            } else {
                (pair.first().x.toLong() * pair.last().y.toLong())
            }
        }

        // S2 = y1*x2 + y2*x3 + y3*x1
        val s2 = vertices.windowed(2, partialWindows = true).sumOf { pair ->
            if (pair.size == 1) {
                (pair.first().y.toLong() * vertices.first().x.toLong())
            } else {
                (pair.first().y.toLong() * pair.last().x.toLong())
            }
        }

        // A = 1/2 * |S1 - S2|
        val area = 0.5 * abs(s1 - s2)
        val perimeterLength = calculatePerimeter(instructions)
        return area + (perimeterLength / 2) + 1
    }

    private fun calculatePerimeter(instructions: List<Instruction>): Long {
        return instructions.sumOf { it.amount.toLong() }
    }

    data class Instruction(val direction: Direction, val amount: Int) {
        companion object {
            fun fromString(string: String, useColour: Boolean = false): Instruction {
                val (directionChar, amount, colour) = string.split(" ")
                return if (useColour) {
                    val colourAmount = colour.substring(2, 7).toInt(16)
                    val direction = when(colour[7]) {
                        '0' -> Direction.R
                        '1' -> Direction.D
                        '2' -> Direction.L
                        '3' -> Direction.U
                        else -> throw IllegalArgumentException("Unknown direction ${colour[7]}")
                    }
                    Instruction(direction, colourAmount)
                } else {
                    Instruction(Direction.valueOf(directionChar), amount.toInt())
                }
            }
        }
    }

    enum class Direction(val coordinateDiff: Coordinate) {
        U(Coordinate(0, -1)),
        D(Coordinate(0, 1)),
        L(Coordinate(-1, 0)),
        R(Coordinate(1, 0));
    }
}

fun main() {
    val lavaductLagoon = LavaductLagoon(readInputLines(18))
    println(lavaductLagoon.part1())
    println(lavaductLagoon.part2())
}
