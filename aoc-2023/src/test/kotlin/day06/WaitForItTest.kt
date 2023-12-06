package day06

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class WaitForItTest {
    private val testInput = """Time:      7  15   30
Distance:  9  40  200""".lines()

    private val testSubject = WaitForIt(testInput)

    @Test
    fun `should find number of ways to win`() {
        assertThat(testSubject.part1()).isEqualTo(288)
    }

    @Test
    fun `should find number of ways to win accounting for kerning`() {
        assertThat(testSubject.part2()).isEqualTo(71503)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(WaitForIt(readInputLines(6)).part1())
            .isEqualTo(1195150)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(WaitForIt(readInputLines(6)).part2())
            .isEqualTo(42550411)
    }
}
