package day23

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class UnstableDiffusionTest {
    private val testInput = """
        ....#..
        ..###.#
        #...#.#
        .#...##
        #.###..
        ##.#.##
        .#..#..
    """.trimIndent().lines()

    private val testSubject = UnstableDiffusion(testInput)

    @Test
    fun `should find ground tiles in smallest rectangle for test input`() {
        assertThat(testSubject.part1()).isEqualTo(110)
    }

    @Test
    fun `should find round number for test input`() {
        assertThat(testSubject.part2()).isEqualTo(20)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(UnstableDiffusion(readInputLines(23)).part1())
            .isEqualTo(3923)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(UnstableDiffusion(readInputLines(23)).part2())
            .isEqualTo(1019)
    }
}
