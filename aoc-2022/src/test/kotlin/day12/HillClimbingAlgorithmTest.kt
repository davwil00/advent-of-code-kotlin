package day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class HillClimbingAlgorithmTest {
    private val testInput = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent().lines()

    private val testSubject = HillClimbingAlgorithm(testInput)

    @Test
    fun `should find shortest path`() {
        assertThat(testSubject.part1())
            .isEqualTo(31)
    }

    @Test
    fun `should find shortest scenic path`() {
        assertThat(testSubject.part2())
            .isEqualTo(29)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(HillClimbingAlgorithm(readInputLines(12)).part1())
            .isEqualTo(412)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(HillClimbingAlgorithm(readInputLines(12)).part2())
            .isEqualTo(402)
    }
}
