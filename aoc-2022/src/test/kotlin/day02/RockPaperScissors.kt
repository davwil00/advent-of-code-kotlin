package day02

import day02.RockPaperScissors.Outcome.DRAW
import day02.RockPaperScissors.Outcome.LOSE
import day02.RockPaperScissors.Outcome.WIN
import day02.RockPaperScissors.Shape
import day02.RockPaperScissors.Shape.PAPER
import day02.RockPaperScissors.Shape.ROCK
import day02.RockPaperScissors.Shape.SCISSORS
import utils.readInputLines

class RockPaperScissors(private val input: List<String>) {

    private val strategy = parseStrategy()

    private fun parseStrategy(): Strategy = input.map { line ->
            val (move, response) = line.split(" ")
            val opponentShape = Shape.fromEncodedValue(move)
            val responseShape = Shape.fromEncodedValue(response)
            Pair(opponentShape, responseShape)
        }

    fun calculateScore() = strategy.sumOf { move ->
            calculateScore(move)
        }

    fun calculateScoreForDesiredOutcome() = strategy.sumOf { move ->
            calculateScoreForDesiredOutcome(move)
        }

    private fun calculateScore(move: Move): Int {
        val outcomeScore = getOutcome(move).score
        return move.second.score + outcomeScore
    }

    private fun calculateScoreForDesiredOutcome(move: Move): Int {
        val desiredOutcome = Outcome.fromEncodedValue(move.second.encodedValue2)
        val moveScore = when (calculateMoveForOutcome(move.first, desiredOutcome)) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS-> 3
        }

        val outcomeScore = desiredOutcome.score
        return moveScore + outcomeScore
    }

    private fun calculateMoveForOutcome(opponentMove: Shape, desiredOutcome: Outcome) = when(opponentMove) {
        ROCK -> {
            when (desiredOutcome) {
                WIN -> PAPER
                DRAW -> ROCK
                LOSE -> SCISSORS
            }
        }

        PAPER -> {
            when (desiredOutcome) {
                WIN -> SCISSORS
                DRAW -> PAPER
                LOSE -> ROCK
            }
        }

        SCISSORS -> {
            when (desiredOutcome) {
                WIN -> ROCK
                DRAW -> SCISSORS
                LOSE -> PAPER
            }
        }
    }

    private fun getOutcome(move: Move) = when(move.first) {
            ROCK -> {
                when (move.second) {
                    ROCK -> DRAW
                    PAPER -> WIN
                    SCISSORS -> LOSE
                }
            }

            PAPER -> {
                when (move.second) {
                    ROCK -> LOSE
                    PAPER -> DRAW
                    SCISSORS -> WIN
                }
            }

            SCISSORS -> {
                when (move.second) {
                    ROCK -> WIN
                    PAPER -> LOSE
                    SCISSORS -> DRAW
                }
            }
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

        companion object {
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
