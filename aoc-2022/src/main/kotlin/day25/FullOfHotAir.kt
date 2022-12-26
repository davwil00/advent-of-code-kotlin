package day25

import utils.readInputLines
import utils.splitToString
import kotlin.math.pow

class FullOfHotAir(val input: List<SNAFU>) {

    fun part1(): SNAFU {
        val sum = input.sumOf { it.toDecimal() }
        return sum.toSNAFU()
    }
}
typealias SNAFU = String
// Based on balanced ternary but quinternary
fun Long.toSNAFU(): String {
    val digits = this.toString(5).splitToString().map { it.toInt() }.reversed().toMutableList()
    val converted = mutableListOf<String>()
    for (i in digits.indices) {
        converted.add(when (val digit = digits[i]) {
            0, 1, 2 -> digit.toString()
            3 -> {
                digits[i + 1]++
                "="
            }
            4 -> {
                digits[i + 1]++
                "-"
            }
            else -> {
                digits[i + 1]++
                "0"
            }
        })
    }
    return converted.reversed().joinToString("")
}

fun SNAFU.toDecimal(): Long {
    val digits = this.splitToString()
    val converted = digits.reversed().mapIndexed { idx, digit ->
        when (digit) {
            "0", "1", "2" -> digit.toInt()
            "=" -> -2
            "-" -> -1
            else -> throw IllegalStateException("Unexpected digit $digit")
        } * (5.0).pow(idx)
    }
    return converted.sum().toLong()
}

fun main() {
    val fullOfHotAir = FullOfHotAir(readInputLines(25))
    println(fullOfHotAir.part1())
}
