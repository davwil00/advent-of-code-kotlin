package day14

import utils.Coordinate
import utils.readInputLines
import kotlin.math.max
import kotlin.math.min

class RegolithReservoir(private val input: List<String>) {

    companion object {
        private val SAND_START = Coordinate(500, 0)
    }

    private fun initMap()  = input.flatMap { line ->
            val rockLines = line.split(" -> ")
                .map {
                    val (x, y) = it.split(",")
                    Coordinate(x.toInt(), y.toInt())
                }
            rockLines.zipWithNext().flatMap { (coordinate1, coordinate2) ->
                if (coordinate1.x == coordinate2.x) {
                    (min(coordinate1.y, coordinate2.y)..max(coordinate1.y, coordinate2.y)).map {
                        Coordinate(coordinate1.x, it) to '#'
                    }
                } else {
                    (min(coordinate1.x, coordinate2.x)..max(coordinate1.x, coordinate2.x)).map {
                        Coordinate(it, coordinate1.y) to '#'
                    }
                }
            }
        }.toMap().toMutableMap()

    fun part1(): Int {
        val map = initMap()
        val minX = map.keys.minByOrNull { it.x }!!.x
        val maxX = map.keys.maxByOrNull { it.x }!!.x

        fun inAbyss(coordinate: Coordinate) = coordinate.x < minX || coordinate.x > maxX

        fun Coordinate.fallToVoid(): Coordinate {
            val coordinatesToTry = sequenceOf(
                Coordinate(x, y + 1),
                Coordinate(x-1, y + 1),
                Coordinate(x+1, y + 1)
            )
            return coordinatesToTry.firstOrNull { it !in map } ?: this
        }

        var sand: Coordinate

        do {
            var newPosition = SAND_START
            do {
                sand = newPosition
                newPosition = sand.fallToVoid()
            } while (sand != newPosition && !inAbyss(sand))
            map[sand] = 'o'
        } while(!inAbyss(sand))

        return map.values.count{ it == 'o' } - 1
    }

    fun part2(): Int {
        val map = initMap()
        val maxY = map.keys.maxByOrNull { it.y }!!.y + 2
        var sand: Coordinate

        fun Coordinate.fallToFloor(maxY: Int): Coordinate {
            val coordinatesToTry = sequenceOf(
                Coordinate(x, y + 1),
                Coordinate(x-1, y + 1),
                Coordinate(x+1, y + 1)
            )
            return coordinatesToTry.firstOrNull { it !in map && it.y != maxY } ?: this
        }

        do {
            var newPosition = SAND_START
            do {
                sand = newPosition
                newPosition = sand.fallToFloor(maxY)
            } while (sand != newPosition)
            map[sand] = 'o'
        } while(sand != SAND_START)

        return map.values.count{ it == 'o' }
    }
}

fun main() {
    val regolithReservoir = RegolithReservoir(readInputLines(14))
    println(regolithReservoir.part1())
    println(regolithReservoir.part2())
}
