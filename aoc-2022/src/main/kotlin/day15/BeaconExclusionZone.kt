package day15

import utils.Coordinate
import utils.readInputLines

class BeaconExclusionZone(input: List<String>) {

    companion object {
        private val INPUT_REGEX = Regex("""Sensor at x=([-]?\d+), y=([-]?\d+): closest beacon is at x=([-]?\d+), y=([-]?\d+)""")
    }

    private val sensors = input.map {
        val match = INPUT_REGEX.matchEntire(it)!!
        val (sensorX, sensorY, beaconX, beaconY) = match.destructured
        Sensor(Coordinate(sensorX.toInt(), sensorY.toInt()), Coordinate(beaconX.toInt(), beaconY.toInt()))
    }

    fun part1(targetY: Int = 2000000): Int {
        val beaconLocations = sensors.map { it.closestBeacon }.toSet()
        val minMapX = sensors.minOfOrNull { it.location.x - it.getExclusionRadius() }!!
        val maxMapX = sensors.maxOfOrNull { it.location.x + it.getExclusionRadius() }!!
        return (minMapX .. maxMapX).filter { x ->
            sensors.any { sensor ->
                sensor.location.manhattanDistanceTo(Coordinate(x, targetY)) <= sensor.getExclusionRadius()
            }
        }.filter {
            !beaconLocations.contains(Coordinate(it, targetY))
        }.size
    }

    /**
     * Theory here is that to reduce the search space, consider that due to the layout
     * of the exclusion zones and that there is just one answer, the gap between zones must be 1.
     * Just search those edges 1 away from the exclusion zones.
     */
    fun part2(searchSpaceSize: Int = 4000000): Long {
        sensors.forEach { sensor ->
            sensor.getCoordinatesOnCircleWithinBounds(searchSpaceSize).forEach { coordinate ->
                if (sensors.none { it.location.manhattanDistanceTo(coordinate) <= it.getExclusionRadius() }) {
                    return coordinate.x * 4000000L + coordinate.y
                }
            }
        }

        throw IllegalStateException("Unable to find beacon")
    }

    class Sensor(val location: Coordinate, val closestBeacon: Coordinate) {
        fun getExclusionRadius() = location.manhattanDistanceTo(closestBeacon)
        fun getCoordinatesOnCircleWithinBounds(max: Int): List<Coordinate> {
            val radius = getExclusionRadius() + 1
            return (0..radius).flatMap {
                listOf(
                    Coordinate(location.x - radius + it, location.y + it),
                    Coordinate(location.x + radius - it, location.y - it),
                    Coordinate(location.x - it, location.y - radius + it),
                    Coordinate(location.x + it, location.y + radius - it)
                )
            }
            .filter { it.x in 0..max && it.y in 0 .. max }
        }
    }
}

fun main() {
    val beaconExclusionZone = BeaconExclusionZone(readInputLines(15))
    println(beaconExclusionZone.part1())
    println(beaconExclusionZone.part2())
}
