package day08

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.Coordinate
import utils.readInputLines

class TreetopTreeHouseTest {
    private val testInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent().lines()

    private val testSubject = TreetopTreeHouse(testInput)

    @Test
    fun `should count number of visible trees`() {
        assertThat(testSubject.part1())
            .isEqualTo(21)
    }

    @Test
    fun `should find the scenic score for the best location`() {
        assertThat(testSubject.part2())
            .isEqualTo(8)
    }

    @Test
    fun `should find the scenic score`() {
        assertThat(testSubject.scenicScore(Coordinate(2, 3), 5))
            .isEqualTo(8)
    }

    @Test
    fun `should find the scenic score2`() {
        assertThat(testSubject.scenicScore(Coordinate(2, 1), 5))
            .isEqualTo(4)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(TreetopTreeHouse(readInputLines(8)).part1())
            .isEqualTo(1676)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(TreetopTreeHouse(readInputLines(8)).part2())
            .isEqualTo(313200)
    }
}
