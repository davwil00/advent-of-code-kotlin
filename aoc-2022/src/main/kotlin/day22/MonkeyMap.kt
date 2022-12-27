package day22

import day22.MonkeyMap.Direction.DOWN
import day22.MonkeyMap.Direction.LEFT
import day22.MonkeyMap.Direction.RIGHT
import day22.MonkeyMap.Direction.UP
import org.slf4j.LoggerFactory
import utils.Coordinate
import utils.splitToString
import java.io.File

class MonkeyMap(private val input: String) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun part1(): Int {
        val (map, path) = parseInput()
        var currentPosition = Position(map.keys.filter { it.y == 1 }.minByOrNull { it.x }!!, RIGHT)

        path.forEach { instruction ->
            for (i in 0 until instruction.number) {
                var newPosition = currentPosition.move()
                if (!map.containsKey(newPosition.coordinate)) {
                    val newCoordinate = when (currentPosition.direction) {
                        LEFT -> map.keys.filter { it.y == currentPosition.coordinate.y }.maxByOrNull { it.x }!!
                        RIGHT -> map.keys.filter { it.y == currentPosition.coordinate.y }.minByOrNull { it.x }!!
                        UP -> map.keys.filter { it.x == currentPosition.coordinate.x }.maxByOrNull { it.y }!!
                        DOWN -> map.keys.filter { it.x == currentPosition.coordinate.x }.minByOrNull { it.y }!!
                    }
                    newPosition = Position(newCoordinate, currentPosition.direction)
                }

                if (map[newPosition.coordinate] == "#") {
                    break
                }
                currentPosition = newPosition
            }
            currentPosition.direction = currentPosition.direction.turn(instruction.direction)
        }

        return currentPosition.password()
    }

    fun part2(): Int {
        val (map, path) = parseInput()
        var currentPosition = Position(map.keys.filter { it.y == 1 }.minByOrNull { it.x }!!, RIGHT)
        val cube = Cube()
        val logIdx = Integer.MAX_VALUE

        path.forEachIndexed { idx, instruction ->
            if (idx < logIdx) logger.debug("Moving ${instruction.number} steps from $currentPosition")
            for (i in 0 until instruction.number) {
                var newPosition = currentPosition.move()
                if (!map.containsKey(newPosition.coordinate)) {
                    newPosition = cube.getWrappedCoordinate(currentPosition)
                    if (idx < logIdx) logger.debug("Wrapping round to $newPosition")
                }

                if (map[newPosition.coordinate] == "#") {
                    break
                }
                currentPosition = newPosition
                if (idx < logIdx) logger.debug("new position: $currentPosition")
            }
            currentPosition.direction = currentPosition.direction.turn(instruction.direction)
            if (idx < logIdx) logger.debug("turning to ${currentPosition.direction}")
        }

        return currentPosition.password()
    }

    class Cube {
        private val coordinates = (0 until 4).flatMap { i ->
            (0 until 4).map { j ->
                (4 * i) + j + 1 to Coordinate((50 * j) + 1, (50 * i) + 1)
            }
        }.toMap()

        companion object {
            const val FAR_EDGE = 49
        }

        fun getWrappedCoordinate(position: Position): Position {
            val (coordinate, direction) = position
            return when (val section = getSection(coordinate)) {
                2 -> when (direction) {
                    UP -> Position(section(13) + Coordinate(0, coordinate.offset().x), RIGHT)
                    LEFT -> Position(section(9) + Coordinate(0, FAR_EDGE - coordinate.offset().y), RIGHT)
                    else -> throw IllegalStateException("Tried to move $direction from section $section")
                }
                3 -> when (direction) {
                    UP -> Position(section(13) + Coordinate(coordinate.offset().x, FAR_EDGE), UP)
                    RIGHT -> Position(section(10) + Coordinate(FAR_EDGE, FAR_EDGE - coordinate.offset().y), LEFT)
                    DOWN -> Position(section(6) + Coordinate(FAR_EDGE, coordinate.offset().x), LEFT)
                    else -> throw IllegalStateException("Tried to move $direction from section $section")
                }
                6 -> when (direction) {
                    LEFT -> Position(section(9) + Coordinate(coordinate.offset().y, 0), DOWN)
                    RIGHT -> Position(section(3) + Coordinate(coordinate.offset().y, FAR_EDGE), UP)
                    else -> throw IllegalStateException("Tried to move $direction from section $section")
                }
                9 -> when (direction) {
                    UP -> Position(section(6) + Coordinate(0, coordinate.offset().x), RIGHT)
                    LEFT -> Position(section(2) + Coordinate(0, FAR_EDGE - coordinate.offset().y), RIGHT)
                    else -> throw IllegalStateException("Tried to move $direction from section $section")
                }
                10 -> when (direction) {
                    RIGHT -> Position(section(3) + Coordinate(FAR_EDGE, FAR_EDGE - coordinate.offset().y), LEFT)
                    DOWN -> Position(section(13) + Coordinate(FAR_EDGE, coordinate.offset().x), LEFT)
                    else -> throw IllegalStateException("Tried to move $direction from section $section")
                }
                13 -> when (direction) {
                    LEFT -> Position(section(2) + Coordinate(coordinate.offset().y, 0), DOWN)
                    DOWN -> Position(section(3) + Coordinate(coordinate.offset().x, 0), DOWN)
                    RIGHT -> Position(section(10) + Coordinate(coordinate.offset().y, FAR_EDGE), UP)
                    else -> throw IllegalStateException("Tried to move $direction from section $section")
                }
                else -> throw IllegalStateException("Unexpected section $section for coordinate $coordinate")
            }
        }

        private fun getSection(coordinate: Coordinate): Int {
            val x = coordinates.entries.firstOrNull {
                coordinate.x in it.value.x until it.value.x + 50 &&
                coordinate.y in it.value.y until it.value.y + 50
            }?.key
            return x!!
        }

        private fun section(section: Int) = coordinates.getValue(section)

        private fun Coordinate.offset() = Coordinate((x - 1) % 50, (y - 1) % 50)
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
    class Input(val map: Map<Coordinate, String>, private val path: List<Instruction>) {
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
    data class Position(var coordinate: Coordinate, var direction: Direction) {
        fun move() = Position(coordinate + direction.delta, direction)
        fun password() = coordinate.y * 1000 + coordinate.x * 4 + direction.value
    }
}

fun main() {
    val monkeyMap = MonkeyMap(File("src/main/resources/day22/input.txt").readText().trimEnd())
    println(monkeyMap.part1())
    println(monkeyMap.part2())
}
