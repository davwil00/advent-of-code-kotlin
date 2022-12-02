package day01

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import utils.readInput

class CalorieCountingTest {
    private val testInput = """1000
                          |2000
                          |3000
                          |
                          |4000
                          |
                          |5000
                          |6000
                          |
                          |7000
                          |8000
                          |9000
                          |
                          |10000""".trimMargin()

    private val testSubject = CalorieCounting(testInput)

    @Test
    fun `should find total calories for elf with most calories`() {
        assertThat(testSubject.findTotalCaloriesForElfWithMaxCalories())
            .isEqualTo(24000)
    }

    @Test
    fun `should find total calories for top 3 elves with most calories`() {
        assertThat(testSubject.findTotalCaloriesForTop3Elves())
            .isEqualTo(45000)
    }

    @Test
    fun `should get correct answer for part 1`() {
        assertThat(CalorieCounting(readInput(1)).findTotalCaloriesForElfWithMaxCalories())
            .isEqualTo(67450)
    }

    @Test
    fun `should get correct answer for part 2`() {
        assertThat(CalorieCounting(readInput(1)).findTotalCaloriesForTop3Elves())
            .isEqualTo(199357)
    }
}
