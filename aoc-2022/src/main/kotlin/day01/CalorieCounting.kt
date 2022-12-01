package day01

import utils.readInput

class CalorieCounting(input: String) {

    private val elves = input
        .split("\n\n")
        .map { elf ->
            Elf(elf
                .split("\n")
                .map { cals -> cals.toInt() })
        }


    data class Elf(private val calories: List<Int>) {
        fun totalCalories() = calories.sum()
    }

    fun findTotalCaloriesForElfWithMaxCalories(): Int {
        return elves.maxBy { it.totalCalories() }.totalCalories()
    }

    fun findTotalCaloriesForTop3Elves(): Int {
        return elves.sortedByDescending { it.totalCalories() }
            .take(3)
            .sumOf { it.totalCalories() }
    }
}

fun main() {
    val calorieCounting = CalorieCounting(readInput(1))
    println(calorieCounting.findTotalCaloriesForElfWithMaxCalories())
    println(calorieCounting.findTotalCaloriesForTop3Elves())
}
