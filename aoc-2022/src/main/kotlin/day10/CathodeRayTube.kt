package day10

import utils.printBlock
import utils.readInputLines

class CathodeRayTube(input: List<String>) {
    companion object {
        private val CYCLES_TO_MEASURE = (20 .. 220 step 40).map { it }
        private const val SCREEN_WIDTH = 40
        private const val SCREEN_HEIGHT = 6
    }

    private val program = input.map { command ->
        when {
            command.startsWith("addx") -> {
                val (_, value) = command.split(" ")
                Addx(value.toInt())
            }
            command.startsWith("noop") -> Noop()
            else -> throw IllegalStateException("Unexpected command $command")
        }
    }

    fun part1(): Int {
        val cycleValues = calculateRegisterValues()
        return CYCLES_TO_MEASURE.sumOf { it * cycleValues[it - 1] }
    }

    fun part2() {
        val cycleValues = calculateRegisterValues()

        (0 until SCREEN_HEIGHT).forEach { y ->
            (0 until SCREEN_WIDTH).forEach { x->
                val cycleNum = (y * SCREEN_WIDTH) + x
                if ((x - 1 .. x + 1).contains(cycleValues[cycleNum])) {
                    printBlock()
                } else {
                    print(' ')
                }
            }
            println()
        }
    }

    private fun calculateRegisterValues(): List<Int> {
        val cycleValues = program.fold(mutableListOf(1)) { acc, command ->
            acc.addAll(command.process(acc.last()))
            acc
        }
        return cycleValues
    }

    interface Command {
        fun process(currentValue: Int): List<Int>
    }
    class Addx(private val value: Int): Command {
        override fun process(currentValue: Int) = listOf(currentValue, currentValue + value)
    }
    class Noop : Command {
        override fun process(currentValue: Int) = listOf(currentValue)
    }
}

fun main() {
    val cathodeRayTube = CathodeRayTube(readInputLines(10))
    println(cathodeRayTube.part1())
    cathodeRayTube.part2()
}
