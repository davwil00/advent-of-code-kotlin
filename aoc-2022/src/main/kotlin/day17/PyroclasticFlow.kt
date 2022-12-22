package day17

import day17.PyroclasticFlow.Direction.DOWN
import org.slf4j.LoggerFactory
import utils.Coordinate
import utils.readInput
import java.util.stream.Stream

class PyroclasticFlow(input: String) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val rocks = """
        ####

        .#.
        ###
        .#.

        ..#
        ..#
        ###

        #
        #
        #
        #

        ##
        ##
    """.trimIndent()
        .split("\n\n")
        .mapIndexed { idx, template -> Rock(idx, template) }

    private val jetStream: Iterator<Char> = Stream.iterate(0) { it + 1 }.map { input[it % input.length] }.iterator()
    private val rockStream: Stream<Rock> = Stream.iterate(0) { it + 1 }.map { rocks[it % rocks.size] }

    fun part1(): Int {
        val chamber = mutableMapOf<Coordinate, Char>()
        (0 until 7).forEach {
            chamber[Coordinate(it, 0)] = '-'
        }

        var topRockIdx = 0
        rockStream
            .limit(2022)
            .forEach { rock ->
                // rock appears 3 from the topmost rock
                var rockCoordinate = Coordinate(2, topRockIdx + 4) // bottom left
                printChamberWithRock(chamber, rock, rockCoordinate)
                var falling = true
                while(falling) {
                    // apply jet
                    val direction = Direction.fromJetStreamChar(jetStream.next())
                    if (canMove(rock, rockCoordinate, direction, chamber)) {
                        logger.debug("pushing rock $direction")
                        rockCoordinate += direction.delta
                    } else {
                        logger.debug("can't push rock $direction")
                    }
                    printChamberWithRock(chamber, rock, rockCoordinate)
                    // fall
                    if (canMove(rock, rockCoordinate, DOWN, chamber)) {
                        rockCoordinate -= Coordinate(0, 1)
                        printChamberWithRock(chamber, rock, rockCoordinate)
                    } else {
                        falling = false
                    }
                }
                // add rock to map once it's stopped
                rock.getCoordinates(rockCoordinate).forEach {
                    chamber[it] = '#'
                }

                topRockIdx = chamber.keys.maxOfOrNull { it.y }!!
                drawChamber(chamber, topRockIdx)
            }

        return topRockIdx
    }

    private fun drawChamber(chamber: Map<Coordinate, Char>, topRockIdx: Int) {
        (topRockIdx downTo 0).forEach {y ->
            logger.debug((0 until 7).map { x->
                val coordinate = Coordinate(x, y)
                if (chamber.containsKey(coordinate)) {
                    chamber[coordinate]
                } else {
                    "."
                }
            }.joinToString(""))
        }
        logger.debug("")
    }

    private fun printChamberWithRock(chamber: Map<Coordinate, Char>, rock: Rock, bottomLeft: Coordinate) {
        val rockCoordinates = rock.getCoordinates(bottomLeft)
        val topRockIdx = chamber.keys.maxOfOrNull { it.y }!! + 4 + rock.height
        (topRockIdx downTo 0).forEach {y ->
            logger.debug((0 until 7).map { x->
                val coordinate = Coordinate(x, y)
                when {
                    (chamber.containsKey(coordinate)) -> chamber[coordinate]
                    rockCoordinates.contains(coordinate) -> '@'
                    else -> "."
                }
            }.joinToString(""))
        }
        logger.debug("")
    }

    private fun canMove(rock: Rock, coordinate: Coordinate, direction: Direction, map: Map<Coordinate, Char>): Boolean {
        val coordinates = rock.getCoordinates(coordinate + direction.delta)
        return coordinates.none { map.containsKey(it) } && coordinates.all { it.x in 0 until 7 }
    }

    data class Rock(val num: Int, val shape: String) {
        val height = shape.lines().size
        val width = shape.lines()[0].length

        fun getCoordinates(bottomLeft: Coordinate): List<Coordinate> {
            return shape.lines().reversed().flatMapIndexed { y, line ->
                line.chunked(1).mapIndexedNotNull { x, char ->
                    when (char) {
                        "#" -> Coordinate(x, y) + bottomLeft
                        else -> null
                    }
                }
            }
        }

        override fun toString() = shape
    }

    enum class Direction(val delta: Coordinate) {
        LEFT(Coordinate(-1, 0)),
        RIGHT(Coordinate(1, 0)),
        DOWN(Coordinate(0, -1));

        companion object {
            fun fromJetStreamChar(char: Char) = if (char == '<') LEFT else RIGHT
        }
    }
}

fun main() {
    val pyroclasticFlow = PyroclasticFlow(readInput(17))
    println(pyroclasticFlow.part1())
    //println(pyroclasticFlow.part2())
}
