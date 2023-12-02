package day02

import utils.readInputLines

class CubeConundrum(private val input: List<String>) {

    fun part1(): Int {
        val constraint = Draw(12, 13, 14)
        val games = parseInput()
        return games.filter { game ->
            game.draws.all { it.isPossible(constraint) }
        }.sumOf { it.gameId }
    }

    fun part2(): Int {
        val games = parseInput()
        return games.sumOf { it.power() }
    }

    private fun parseInput() = input
        .map { it.split(":") }
        .map { (game, sets) ->
            val draws = sets.split(";")
                .map { set ->
                    val draws = setRegex.findAll(set)
                        .map {
                            val (number, colour) = it.destructured
                            CubeSubset(number.toInt(), colour)
                        }
                    Draw(
                        draws.totalOfColour("red"),
                        draws.totalOfColour("green"),
                        draws.totalOfColour("blue")
                    )
                }

            val gameId = game.split(" ")[1].toInt()
            Game(gameId, draws)
        }

    data class Game(val gameId: Int, val draws: List<Draw>) {
        fun power() = draws.maxOf{ it.red } * draws.maxOf { it.green } * draws.maxOf { it.blue }
    }
    data class CubeSubset(val number: Int, val colour: String)
    data class Draw(val red: Int, val green: Int, val blue: Int) {
        fun isPossible(constraint: Draw) =
            red <= constraint.red &&
                    green <= constraint.green &&
                    blue <= constraint.blue
    }
    private fun Sequence<CubeSubset>.totalOfColour(colour: String) = filter { it.colour == colour }.sumOf { it.number }

    companion object {
        private val setRegex = Regex("(\\d+) (red|green|blue)")
    }
}

fun main() {
    val cubeConundrum = CubeConundrum(readInputLines(2))
    println(cubeConundrum.part1())
    println(cubeConundrum.part2())
}
