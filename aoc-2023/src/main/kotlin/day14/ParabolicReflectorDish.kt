package day14

import utils.Coordinate
import utils.readGrid
import utils.readInputLines

class ParabolicReflectorDish(private val input: List<String>) {
    private val originalMap = readGrid(input)

    fun part1(): Int {
        val newMap = rollNorth()
        return calculateLoadNorth(newMap)
    }

    private fun rollNorth(): Map<Coordinate, String> {
        // for each O, see if there's room to move it 'up'
        val rockLocations = originalMap.filter { it.value == "O" && it.key.y != 0 }
        val newMap = originalMap.toMutableMap()
        rockLocations.forEach { rockLocation ->
            val newLocation = findNewLocationFor(rockLocation.key, newMap)
            if (newLocation != rockLocation.key) {
                newMap[rockLocation.key] = "."
                newMap[newLocation] = "O"
            }
        }
        return newMap
    }

    private fun calculateLoadNorth(map: Map<Coordinate, String>): Int {
        return input.indices.sumOf { y ->
            map.count { it.key.y == y && it.value == "O" } * (input.size - y)
        }
    }

    private tailrec fun findNewLocationFor(rockLocation: Coordinate, newMap: Map<Coordinate, String>): Coordinate {
        if (rockLocation.y == 0) {
            return rockLocation
        }
        val locationToCheck = rockLocation - Coordinate(0, 1)
        val currentOccupant = newMap.getValue(locationToCheck)
        return if (currentOccupant == "." ) findNewLocationFor(locationToCheck, newMap) else rockLocation
    }
}

fun main() {
    val parabolicReflectorDish = ParabolicReflectorDish(readInputLines(14))
    println(parabolicReflectorDish.part1())
    //println(parabolicreflectordish.part2())
}
