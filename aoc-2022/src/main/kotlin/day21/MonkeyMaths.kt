package day21

import utils.readInputLines

class MonkeyMaths(input: List<String>) {

    private val monkeys = input.associate { definition ->
        val matchResult = OperationMonkey.DEFINITION_REGEX.matchEntire(definition)
        val monkey = if (matchResult != null) {
            OperationMonkey.fromDefinition(matchResult)
        } else {
            NumberMonkey.fromDefinition(definition)
        }
        monkey.name to monkey
    }

    fun part1(): Long {
        val rootMonkey = monkeys.getValue("root") as OperationMonkey
        return resolveMonkey(rootMonkey)
    }

    fun part2(): Long {
        val rootMonkey = monkeys.getValue("root") as OperationMonkey
        val monkey1 = monkeys.getValue(rootMonkey.monkey1) as OperationMonkey
        val monkey2 = monkeys.getValue(rootMonkey.monkey2) as OperationMonkey
        return if (scanForHumn(monkey1)) {
            solveHumn(resolveMonkey(monkey2), monkey1)
        } else {
            solveHumn(resolveMonkey(monkey1), monkey2)
        }
    }

    private fun resolveMonkey(monkey: Monkey): Long {
        if (monkey is NumberMonkey) {
            return monkey.number
        }
        monkey as OperationMonkey
        val monkey1 = resolveMonkey(monkeys.getValue(monkey.monkey1))
        val monkey2 = resolveMonkey(monkeys.getValue(monkey.monkey2))
        return monkey.operation.function(monkey1, monkey2)
    }

    private fun scanForHumn(monkey: Monkey): Boolean {
        if (monkey.isHumn()) {
            return true
        }
        else if (monkey is OperationMonkey) {
            return scanForHumn(monkeys.getValue(monkey.monkey1)) || scanForHumn(monkeys.getValue(monkey.monkey2))
        }

        return false
    }

    private fun solveHumn(target: Long, monkey: OperationMonkey): Long {
        val monkey1 = monkeys.getValue(monkey.monkey1)
        val monkey2 = monkeys.getValue(monkey.monkey2)
        return when {
            monkey1.isHumn() -> reverseEquation(target, resolveMonkey(monkey2), monkey.operation, false)
            monkey2.isHumn() -> reverseEquation(target, resolveMonkey(monkey1), monkey.operation, true)
            scanForHumn(monkey1) -> {
                val newTarget = reverseEquation(target, resolveMonkey(monkey2), monkey.operation, false)
                solveHumn(newTarget, monkey1 as OperationMonkey)
            }
            else -> {
                val newTarget = reverseEquation(target, resolveMonkey(monkey1), monkey.operation, true)
                solveHumn(newTarget, monkey2 as OperationMonkey)
            }
        }
    }

    private fun reverseEquation(target: Long, value: Long, operation: Operation, unknownSecond: Boolean): Long {
        return when {
            operation == Operation.DIV && unknownSecond -> value / target
            operation == Operation.MINUS && unknownSecond -> (target - value) * -1
            else -> operation.oppositeFunction(target, value)
        }
    }

    abstract class Monkey(val name: String) {
        fun isHumn() = name == "humn"
    }
    class NumberMonkey(name: String, val number: Long): Monkey(name) {
        companion object {
            fun fromDefinition(definition: String): NumberMonkey {
                val (name, value) = definition.split(": ")
                return NumberMonkey(name, value.toLong())
            }
        }
    }
    class OperationMonkey(name: String, val monkey1: String, val monkey2: String, val operation: Operation): Monkey(name) {
        companion object {
            val DEFINITION_REGEX = Regex("""([a-z]{4}): ([a-z]{4}) ([+\-/*]) ([a-z]{4})""")

            fun fromDefinition(matchResult: MatchResult): OperationMonkey {
                val (name, monkey1, operationSymbol, monkey2) = matchResult.destructured
                val operation = Operation.fromSymbol(operationSymbol)
                return OperationMonkey(name, monkey1, monkey2, operation)
            }
        }
    }

    enum class Operation(val symbol: String, val function: (Long, Long) -> Long, val oppositeFunction: (Long, Long) -> Long) {
        PLUS("+", Long::plus, Long::minus),
        MINUS("-", Long::minus, Long::plus),
        TIMES("*", Long::times, Long::div),
        DIV("/", Long::div, Long::times);

        companion object {
            fun fromSymbol(symbol: String) = values().first { it.symbol == symbol }
        }
    }
}

fun main() {
    val monkeyMaths = MonkeyMaths(readInputLines(21))
    println(monkeyMaths.part1())
    println(monkeyMaths.part2())
}
