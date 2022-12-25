package day22

import day22.MonkeyMap.Direction.DOWN
import day22.MonkeyMap.Direction.LEFT
import day22.MonkeyMap.Direction.RIGHT
import day22.MonkeyMap.Direction.UP
import utils.Coordinate
import utils.splitToString
import java.io.File

class MonkeyMap(private val input: String) {

    fun part1(): Int {
        val (map, path) = parseInput()
        var currentCoordinate = map.keys.filter { it.y == 1 }.minByOrNull { it.x }!!
        var currentDirection = RIGHT

        path.forEach { instruction ->
            for (i in 0 until instruction.number) {
                var newCoordinate = currentCoordinate + currentDirection.delta
                if (!map.containsKey(newCoordinate)) {
                    newCoordinate = when (currentDirection) {
                        LEFT -> map.keys.filter { it.y == currentCoordinate.y }.maxByOrNull { it.x }!!
                        RIGHT -> map.keys.filter { it.y == currentCoordinate.y }.minByOrNull { it.x }!!
                        UP -> map.keys.filter { it.x == currentCoordinate.x }.maxByOrNull { it.y }!!
                        DOWN -> map.keys.filter { it.x == currentCoordinate.x }.minByOrNull { it.y }!!
                    }
                }

                if (map[newCoordinate] == "#") {
                    break
                }
                currentCoordinate = newCoordinate
            }
            currentDirection = currentDirection.turn(instruction.direction)
        }

        return currentCoordinate.y * 1000 + currentCoordinate.x * 4 + currentDirection.value
    }

    private fun parseInput(): Input {
        val (map, path) = input.split("\n\n")
        return Input(parseMap(map), parsePath(path))
    }

    private fun parseMap(map: String): Map<Coordinate, String> {
        return map.lines()
            .flatMapIndexed { y, line ->
                line.splitToString()
                    .mapIndexedNotNull { x, char ->
                        if (char != " ") {
                            Coordinate(x + 1, y + 1) to char
                        } else {
                            null
                        }
                    }
            }.toMap()
    }

    private fun parsePath(path: String): List<Instruction> {
        val iter = path.iterator()
        val instructions = mutableListOf<Instruction>()
        var currentInstruction = ""
        while (iter.hasNext()) {
            val currentChar = iter.nextChar()
            if (currentChar.isLetter()) {
                instructions.add(Instruction(currentInstruction.toInt(), currentChar))
                currentInstruction = ""
            } else {
                currentInstruction += currentChar
            }
        }
        instructions.add(Instruction(currentInstruction.toInt(), 'S'))
        return instructions
    }

    class Instruction(val number: Int, val direction: Char)
    class Input(val map: Map<Coordinate, String>, val path: List<Instruction>) {
        operator fun component1() = map
        operator fun component2() = path
    }
    enum class Direction(val value: Int, val delta: Coordinate) {
        LEFT(2, Coordinate(-1, 0)),
        RIGHT(0, Coordinate(1, 0)),
        UP(3, Coordinate(0, -1)),
        DOWN(1, Coordinate(0, 1));

        fun turn(direction: Char) = when (direction) {
            'S' -> this
            'L' -> when (this) {
                LEFT -> DOWN
                RIGHT -> UP
                UP -> LEFT
                DOWN -> RIGHT
            }
            else -> when (this) {
                LEFT -> UP
                RIGHT -> DOWN
                UP -> RIGHT
                DOWN -> LEFT
            }
        }
    }
}

fun main() {
    val monkeyMap = MonkeyMap(File("src/main/resources/day22/input.txt").readText().trimEnd())
    println(monkeyMap.part1())
    //println(monkeyMap.part2())
}
