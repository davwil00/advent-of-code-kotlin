package day11

import utils.asInt
import utils.product
import utils.productOf
import utils.readInput
import java.util.*

class MonkeyInTheMiddle(private val input: String) {

    private fun initMonkeys() = input
        .split("\n\n")
        .map {
            Monkey.fromDefinition(it.lines())
        }

    fun part1(): Long {
        val monkeys = initMonkeys()
        repeat(20) {
            simulateRound(monkeys)
        }

        return monkeys.calculateMonkeyBusiness()
    }

    fun part2(): Long {
        val monkeys = initMonkeys()
        val worryReductionFactor = monkeys.productOf { it.divisor }
        val worryLevelReductionOperation = { input: Long -> input % worryReductionFactor }
        repeat(10000) {
            simulateRound(monkeys, worryLevelReductionOperation)
        }

        return monkeys.calculateMonkeyBusiness()
    }

    private fun simulateRound(monkeys: List<Monkey>, worryLevelReductionOperation: (Long) -> Long = { input -> input / 3 }) {
        monkeys.forEach { monkey ->
            while(monkey.items.isNotEmpty()) {
                monkey.numberOfInspections++
                val item = monkey.items.pop()
                val newWorryLevel = worryLevelReductionOperation(monkey.operation(item))
                val nextMonkey = if (monkey.test(newWorryLevel)) monkey.trueOutcome else monkey.falseOutcome
                monkeys[nextMonkey].items += newWorryLevel
            }
        }
    }

    data class Monkey(
        val number: Int,
        val items: LinkedList<Long>,
        val operation: Operation,
        val divisor: Long,
        val trueOutcome: Int,
        val falseOutcome: Int,
        var numberOfInspections: Long = 0
    ) {
        fun test(input: Long) = input % divisor == 0L

        companion object {
            private val OPERATION_REGEX = Regex("""Operation: new = old ([+*]) (old|\d+)""")
            private val TEST_REGEX = Regex("""Test: divisible by (\d+)""")

            fun fromDefinition(definition: List<String>): Monkey {
                val monkeyNumber = definition[0][7].asInt()
                val startingItems = parseStartingItem(definition[1]).toCollection(LinkedList())
                val operation = parseOperation(definition[2])
                val divisor = parseDivisor(definition[3])
                val trueOutcome = definition[4].last().asInt()
                val falseOutcome = definition[5].last().asInt()
                return Monkey(monkeyNumber, startingItems, operation, divisor, trueOutcome, falseOutcome)
            }

            private fun parseStartingItem(startingItemDefinition: String) = startingItemDefinition
                .split(":")[1]
                .split(",")
                .map {
                    it.trim().toLong()
                }

            private fun parseOperation(operationDefinition: String): Operation {
                val match = OPERATION_REGEX.matchEntire(operationDefinition.trim())!!
                val value = match.groupValues[2]
                return when(match.groupValues[1]) {
                    "*" -> if (value == "old") { input: Long -> input * input } else { input: Long -> input * value.toLong() }
                    "+" -> if (value == "old") { input: Long -> input + input } else { input: Long -> input + value.toLong() }
                    else -> throw IllegalArgumentException("Unexpected operator ${match.groupValues[1]}")
                }
            }

            private fun parseDivisor(testDefinition: String) = TEST_REGEX.matchEntire(testDefinition.trim())!!.groupValues[1].toLong()
        }
    }

    fun List<Monkey>.calculateMonkeyBusiness() = this
        .map { it.numberOfInspections }
            .sortedDescending()
            .take(2)
            .product()
}

typealias Operation = (Long) -> Long

fun main() {
    val monkeyInTheMiddle = MonkeyInTheMiddle(readInput(11))
    println(monkeyInTheMiddle.part1())
    println(monkeyInTheMiddle.part2())
}
