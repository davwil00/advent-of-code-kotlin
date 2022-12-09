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

    fun part1() = runSimulation(2)
    fun part2() = runSimulation(10)

    private fun runSimulation(numberOfKnots: Int): Int {
        val positions = MutableList(numberOfKnots) { Coordinate(0, 0) }
        val tailPositions = motions.flatMap { motion ->
            (0 until motion.amount).map {
                positions[0] = positions.first() + motion.direction.movementDelta
                updateOtherKnotPositions(positions)
                positions.last()
            }
        }.toSet()

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
        val delta = when {
            currentPosition == headPosition || currentPosition isAdjacentTo headPosition -> Coordinate(0, 0)
            currentPosition.x == headPosition.x -> if (currentPosition isAbove headPosition) D.movementDelta else U.movementDelta
            currentPosition.y == headPosition.y -> if (currentPosition isLeftOf headPosition) R.movementDelta else L.movementDelta
            headPosition isAbove currentPosition && headPosition isLeftOf currentPosition -> U.movementDelta + L.movementDelta
            headPosition isAbove currentPosition && headPosition isRightOf currentPosition -> U.movementDelta + R.movementDelta
            headPosition isBelow currentPosition && headPosition isLeftOf currentPosition -> D.movementDelta + L.movementDelta
            headPosition isBelow currentPosition && headPosition isRightOf currentPosition -> D.movementDelta + R.movementDelta
            else -> throw IllegalStateException("Unable to determine tail position")
        }
        return currentPosition + delta
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
