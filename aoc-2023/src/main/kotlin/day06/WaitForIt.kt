package day06

import utils.productOf
import utils.readInputLines

class WaitForIt(private val input: List<String>) {
    fun part1(): Long {
        val timesAndDistances = parseInput()
        return timesAndDistances.productOf {
            findNumberOfWaysToWin(it).toLong()
        }
    }

    fun part2(): Long {
        val timeDistance = parseInputWithBadKerning()
        return findNumberOfWaysToWin(timeDistance).toLong()
    }

    private fun findNumberOfWaysToWin(timeDistance: TimeDistance): Int {
        return (1 until timeDistance.time).asSequence()
            .dropWhile { holdTime ->
                calculateDistanceTravelled(holdTime, timeDistance.time) <= timeDistance.distance
            }
            .takeWhile { holdTime ->
                calculateDistanceTravelled(holdTime, timeDistance.time) > timeDistance.distance
            }.count()
    }

    private fun calculateDistanceTravelled(holdTime: Long, totalTime: Long) = (totalTime - holdTime) * holdTime

    private fun parseInput(): List<TimeDistance> {
        val (times, distances) = input
        return times.substringAfter(":").trim().split(Regex(" +"))
            .zip(distances.substringAfter(":").trim().split(Regex(" +")))
            .map { (time, distance) -> TimeDistance(time.toLong(), distance.toLong()) }
    }

    private fun parseInputWithBadKerning(): TimeDistance {
        val (times, distances) = input
        return TimeDistance(
            times.substringAfter(":").replace(" ", "").toLong(),
            distances.substringAfter(":").replace(" ", "").toLong()
        )
    }

    data class TimeDistance(val time: Long, val distance: Long)
}

fun main() {
    val waitForIt = WaitForIt(readInputLines(6))
    println(waitForIt.part1())
    println(waitForIt.part2())
}
