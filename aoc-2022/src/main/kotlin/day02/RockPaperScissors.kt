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
            val (move, response) = line.split(" ")
            val opponentShape = Shape.fromEncodedValue(move)
            val responseShape = Shape.fromEncodedValue(response)
            Pair(opponentShape, responseShape)
        }

    fun calculateScore() = strategy.sumOf { move ->
        val outcomeScore = getOutcome(move).score
        move.second.score + outcomeScore
    }

    fun calculateScoreForDesiredOutcome() = strategy.sumOf { move ->
        val desiredOutcome = Outcome.fromEncodedValue(move.second.encodedValue2)
        val moveScore = calculateMoveForOutcome(move.first, desiredOutcome).score
        val outcomeScore = desiredOutcome.score
        moveScore + outcomeScore
    }

    private fun calculateMoveForOutcome(opponentMove: Shape, desiredOutcome: Outcome) = when(desiredOutcome) {
        WIN -> opponentMove.losesAgainst
        DRAW -> opponentMove
        LOSE -> opponentMove.winsAgainst
    }

    private fun getOutcome(move: Move): Outcome = when {
        move.second.winsAgainst == move.first -> WIN
        move.second == move.first -> DRAW
        move.second.losesAgainst == move.first -> LOSE
        else -> throw IllegalArgumentException("Unexpected scenario")
    }

    enum class Outcome(val score: Int, val encodedValue: String) {
        WIN(6, "Z"),
        DRAW(3, "Y"),
        LOSE(0, "X");

        companion object {
            fun fromEncodedValue(encodedValue: String) = Outcome.values()
                .first { it.encodedValue == encodedValue }
        }
    }

    enum class Shape(val encodedValue1: String, val encodedValue2: String, val score: Int) {
        ROCK("A", "X", 1),
        PAPER("B", "Y", 2),
        SCISSORS("C", "Z", 3);
        lateinit var winsAgainst: Shape
        lateinit var losesAgainst: Shape

        companion object {
            init {
                ROCK.winsAgainst = SCISSORS
                ROCK.losesAgainst = PAPER

                PAPER.winsAgainst = ROCK
                PAPER.losesAgainst = SCISSORS

                SCISSORS.winsAgainst = PAPER
                SCISSORS.losesAgainst = ROCK
            }

            fun fromEncodedValue(encodedValue: String) = Shape.values()
                .first { it.encodedValue1 == encodedValue || it.encodedValue2 == encodedValue }
        }
    }
}

typealias Move = Pair<Shape, Shape>
typealias Strategy = List<Move>

fun main() {
    val rockPaperScissors = RockPaperScissors(readInputLines(2))
    println(rockPaperScissors.calculateScore())
    println(rockPaperScissors.calculateScoreForDesiredOutcome())
}
