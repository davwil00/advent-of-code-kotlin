package day05

import utils.asInt
import java.io.File
import java.util.*

class SupplyStacks(input: List<String>) {

    private val numberOfStacks = input.takeWhile { it.isNotEmpty() }.last().last().asInt()

    private val initialStacks = input.takeWhile { it.isNotEmpty() }
        .dropLast(1)
        .map { row ->
            row.chunked(4)
               .map { col -> if (col[1] == ' ') null else col[1] }
        }.pivot(numberOfStacks)
        .map {stack ->
            stack
                .reversed()
                .toMutableList()
        }
        .onEach { stack -> stack.removeIf { it == null } }

    private val instructions = input
        .asSequence()
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .takeWhile { it.isNotEmpty() }
        .map { INSTRUCTION_REGEX.matchEntire(it) }
        .map { result ->
            val (_, numOfCrates, fromStack, toStack) = result!!.groupValues
            Instruction(numOfCrates.toInt(), fromStack.toInt(), toStack.toInt())}

    companion object {
        private val INSTRUCTION_REGEX = Regex("""move (\d+) from (\d) to (\d)""")
    }

    fun part1(): String {
        val stacks = initialStacks.clone()
        instructions.forEach { instruction ->
            val fromStack = stacks[instruction.fromStack - 1]
            val toStack = stacks[instruction.toStack - 1]

            repeat(instruction.numOfCrates) {
                toStack.push(fromStack.pop())
            }
        }

        return stacks.map { it.first }.joinToString("")
    }

    fun part2(): String {
        val stacks = initialStacks.clone()
        instructions.forEach { instruction ->
            val fromStack = stacks[instruction.fromStack - 1]
            val toStack = stacks[instruction.toStack - 1]

            (0 until instruction.numOfCrates)
                .map { fromStack.pop() }
                .reversed()
                .forEach { toStack.push(it) }
        }

        return stacks.map { it.first }.joinToString("")
    }

    data class Instruction(val numOfCrates: Int, val fromStack: Int, val toStack: Int)
}

fun <T> List<List<T>>.pivot(width: Int): List<List<T?>> =
    (0 until width).map { j ->
        (this.size - 1 downTo 0).map { i ->
            if (this.size > i && this[i].size > j) this[i][j] else null
        }
    }
fun <T> List<List<T>>.clone() = this.map { stack -> stack.map { it }.toCollection(LinkedList()) }

fun main() {
    val supplyStacks = SupplyStacks(File("src/main/resources/day05/input.txt").readText().lines())
    println(supplyStacks.part1())
    println(supplyStacks.part2())
}
