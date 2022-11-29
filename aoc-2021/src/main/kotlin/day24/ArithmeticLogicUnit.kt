package day24

import utils.readInputLines
import utils.splitToString
import java.lang.IllegalStateException
import kotlin.reflect.KFunction2

class ArithmeticLogicUnit {

    private var state = mutableMapOf<Char, Int>()
    private val numberRegex = Regex("""-?\d+""")

    private fun inp(a: Char, value: Int) {
        state[a] = value
    }

    private fun getB(b: String) = if (b.matches(numberRegex)) b.toInt() else state.getValue(b.toCharArray().first())

    fun add(a: Char, b: String) {
        state[a] = state.getValue(a) + getB(b)
    }

    private fun mul(a: Char, b: String) {
        state[a] = state.getValue(a) * getB(b)
    }

    fun div(a: Char, b: String) {
        state[a] = state.getValue(a) / getB(b)
    }

    private fun mod(a: Char, b: String) {
        state[a] = state.getValue(a) % getB(b)
    }

    private fun eql(a: Char, b: String) {
        state[a] = if (state.getValue(a) == getB(b)) 1 else 0
    }

    private fun getModelNumberSequence() = generateSequence(99999999999999) { prev ->
        var nextCandidate = prev - 1
        while (nextCandidate > 5999999999999 && nextCandidate.toString().contains('0')) {
            nextCandidate-=26
        }
        nextCandidate
    }

    fun findSubmarineModelNumber(operationsStrs: List<String>): List<Int> {
        val operations = readOps(operationsStrs)
//        val inputs = getModelNumberSequence().iterator()
//        while (state['z'] != 0) {
            state = mutableMapOf('w' to 0, 'x' to 0, 'y' to 0, 'z' to 0)
//            val input = inputs.next().toString().splitToString().map { it.toInt() }
            val input = "11115111111121".splitToString().map { it.toInt() }
            println("trying $input")
            var inputIndex = 0
            operations.forEach { operation ->
                val prevZ = state['z']
                when (operation) {
                    is InputOperation -> operation.function(operation.a, input[inputIndex++])
                    is NonInputOperation -> operation.function(operation.a, operation.b)
                }
                val newZ = state['z']
//                if (newZ != prevZ) {
                    println("$operation, $state")
//                }
            }

            if (state['z'] == 0) {
                return input
            }
//        }

        throw IllegalStateException("did not find model number")
    }

    private fun readOps(operations: List<String>): List<Operation> {
        return operations.map { operation ->
            if (operation.startsWith("inp")) {
                val (operator, a) = operation.split(" ")
                InputOperation(::inp, a.toCharArray().first())
            } else {
                val (operator, a, b) = operation.split(" ")
                val function = when (operator) {
                    "add" -> ::add
                    "mul" -> ::mul
                    "div" -> ::div
                    "mod" -> ::mod
                    "eql" -> ::eql
                    else -> throw IllegalArgumentException("Unknown operator $operator")
                }

                NonInputOperation(function, a.toCharArray().first(), b)
            }
        }
    }

    sealed interface Operation {
        val function: KFunction2<Char, *, Unit>
    }
    data class NonInputOperation(override val function: KFunction2<Char, String, Unit>, val a: Char, val b: String): Operation {
        override fun toString(): String {
            return "${function.name} $a $b"
        }
    }
    data class InputOperation(override val function: KFunction2<Char, Int, Unit>, val a: Char): Operation {
        override fun toString(): String {
            return "${function.name} $a"
        }
    }
}

fun main() {
    val input = readInputLines(24)
    val arithmeticLogicUnit = ArithmeticLogicUnit()
    val modelNum = arithmeticLogicUnit.findSubmarineModelNumber(input)
    println("Model number is $modelNum")
}
