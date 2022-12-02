package day01

import utils.readInput

class CalorieCounting(input: String) {

    private val elves = input
        .split("\n\n")
        .map { elf ->
            elf
                .lines()
                .sumOf { cals -> cals.toInt() }
        }

    fun findTotalCaloriesForElfWithMaxCalories(): Int {
        return elves.max()
    }

    fun findTotalCaloriesForTop3Elves(): Int {
        return elves.sortedDescending()
            .take(3)
            .sum()
    }
}

fun main() {
    val calorieCounting = CalorieCounting(readInput(1))
    println(calorieCounting.findTotalCaloriesForElfWithMaxCalories())
    println(calorieCounting.findTotalCaloriesForTop3Elves())
}
