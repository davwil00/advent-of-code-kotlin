package day15

import utils.readInput

class LensLibrary(private val input: String) {

    fun part1(): Int {
        return input.split(",").sumOf { hashCode(it) }
    }

    /**
     *     Determine the ASCII code for the current character of the string.
     *     Increase the current value by the ASCII code you just determined.
     *     Set the current value to itself multiplied by 17.
     *     Set the current value to the remainder of dividing itself by 256.
     */
    tailrec fun hashCode(string: String, currentValue: Int = 0): Int {
        if (string.isEmpty()) {
            return currentValue
        }

        val newValue = ((currentValue + string.first().code) * 17) % 256
        return hashCode(string.substring(1), newValue)
    }
}

fun main() {
    val lensLibrary = LensLibrary(readInput(15))
    println(lensLibrary.part1())
    //println(lenslibrary.part2())
}
