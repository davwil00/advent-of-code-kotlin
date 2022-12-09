package day09

import day09.RopeBridge.Direction.U
import day09.RopeBridge.Direction.L
import day09.RopeBridge.Direction.R
import day09.RopeBridge.Direction.D
import utils.Coordinate
import utils.readInputLines

class RopeBridge(input: List<String>) {

    private val motions = input.map {
        val (direction, amount) = it.split(" ")
        Motion(Direction.valueOf(direction), amount.toInt())
    }

    fun part1(): Int {
        var headPosition = Coordinate(0, 0)
        var tailPosition = headPosition
        val tailPositions = mutableSetOf<Coordinate>()
        motions.forEach { motion ->
            repeat(motion.amount) {
                headPosition += motion.direction.movementDelta
                tailPosition = calculateTailPosition(tailPosition, headPosition)
                tailPositions += tailPosition
            }
        }

        return tailPositions.size
    }

    fun part2(): Int {
        val positions = MutableList(10) { Coordinate(0, 0) }
        val tailPositions = mutableSetOf<Coordinate>()
        motions.forEach { motion ->
            repeat(motion.amount) {
                positions[0] = positions.first() + motion.direction.movementDelta
                updateOtherKnotPositions(positions)
                tailPositions += positions.last()
            }
        }

        return tailPositions.size
    }

    private fun updateOtherKnotPositions(positions: MutableList<Coordinate>) {
        (0 until positions.size)
            .windowed(2)
            .forEach { (idx1, idx2) ->
                positions[idx2] = calculateTailPosition(positions[idx2], positions[idx1])
            }
    }

    private fun calculateTailPosition(currentPosition: Coordinate, headPosition: Coordinate): Coordinate {
        return when {
            currentPosition == headPosition || currentPosition isAdjacentTo headPosition -> currentPosition
            currentPosition.x == headPosition.x -> {
                if (currentPosition isAbove headPosition) {
                    currentPosition + D.movementDelta
                }
                else {
                    currentPosition + U.movementDelta
                }
            }
            currentPosition.y == headPosition.y -> {
                if (currentPosition isLeftOf headPosition) {
                    currentPosition + R.movementDelta
                } else {
                    currentPosition + L.movementDelta
                }
            }
            headPosition isAbove currentPosition && headPosition isLeftOf currentPosition -> currentPosition + U.movementDelta + L.movementDelta
            headPosition isAbove currentPosition && headPosition isRightOf currentPosition -> currentPosition + U.movementDelta + R.movementDelta
            headPosition isBelow currentPosition && headPosition isLeftOf currentPosition -> currentPosition + D.movementDelta + L.movementDelta
            headPosition isBelow currentPosition && headPosition isRightOf currentPosition -> currentPosition + D.movementDelta + R.movementDelta
            else -> throw IllegalStateException("Unable to determine tail position")
        }
    }

    data class Motion(val direction: Direction, val amount: Int)
    enum class Direction(val movementDelta: Coordinate) {
        U(Coordinate(0, -1)),
        D(Coordinate(0, 1)),
        L(Coordinate(-1, 0)),
        R(Coordinate(1, 0))
    }
}

fun main() {
    val ropeBridge = RopeBridge(readInputLines(9))
    println(ropeBridge.part1())
    println(ropeBridge.part2())
}
