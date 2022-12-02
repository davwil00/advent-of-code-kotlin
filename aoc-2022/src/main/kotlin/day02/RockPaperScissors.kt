package day02

import day02.RockPaperScissors.Outcome.DRAW
import day02.RockPaperScissors.Outcome.LOSE
import day02.RockPaperScissors.Outcome.WIN
import day02.RockPaperScissors.Shape
import utils.readInputLines
import java.lang.IllegalArgumentException

class RockPaperScissors(private val input: List<String>) {

    private val strategy = parseStrategy()

    private fun parseStrategy(): Strategy = input.map { line ->
            val (opponentShape, response) = line.split(" ")
            Pair(opponentShape.toShape(), response)
        }

    fun calculateScore() = strategy.sumOf { round ->
        val yourShape = round.second.toShape()
        val outcomeScore = getOutcome(round.first, yourShape).score
        yourShape.score + outcomeScore
    }

    fun calculateScoreForDesiredOutcome() = strategy.sumOf { move ->
        val desiredOutcome = move.second.toOutcome()
        val moveScore = calculateMoveForOutcome(move.first, desiredOutcome).score
        moveScore + desiredOutcome.score
    }

    private fun getOutcome(opponentShape: Shape, yourShape: Shape): Outcome = when {
        yourShape winsAgainst opponentShape -> WIN
        yourShape == opponentShape -> DRAW
        opponentShape winsAgainst yourShape -> LOSE
        else -> throw IllegalArgumentException("Unexpected scenario")
    }

    private fun calculateMoveForOutcome(opponentMove: Shape, desiredOutcome: Outcome) = when(desiredOutcome) {
        WIN -> opponentMove.losesTo()
        DRAW -> opponentMove
        LOSE -> opponentMove.winsAgainst
    }

    enum class Outcome(val score: Int, val encodedValue: String) {
        WIN(6, "Z"),
        DRAW(3, "Y"),
        LOSE(0, "X");
    }

    private fun String.toOutcome() = Outcome.values().first { it.encodedValue == this }

    enum class Shape(val encodedValue1: String, val encodedValue2: String, val score: Int) {
        ROCK("A", "X", 1),
        PAPER("B", "Y", 2),
        SCISSORS("C", "Z", 3);
        lateinit var winsAgainst: Shape

        companion object {
            init {
                ROCK.winsAgainst = SCISSORS
                PAPER.winsAgainst = ROCK
                SCISSORS.winsAgainst = PAPER
            }
        }

        fun losesTo() = values().first { it.winsAgainst == this }
        infix fun winsAgainst(other: Shape) = this.winsAgainst == other
    }

    private fun String.toShape() = Shape.values().first { it.encodedValue1 == this || it.encodedValue2 == this }
}

typealias Round = Pair<Shape, String>
typealias Strategy = List<Round>

fun main() {
    val rockPaperScissors = RockPaperScissors(readInputLines(2))
    println(rockPaperScissors.calculateScore())
    println(rockPaperScissors.calculateScoreForDesiredOutcome())
}
