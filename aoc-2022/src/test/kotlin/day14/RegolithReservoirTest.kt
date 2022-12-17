package day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class RegolithReservoirTest {
    private val testInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().lines()

    private val testSubject = RegolithReservoir(testInput)

    @Test
    fun `should count the number of grains of sand before they go into the abyss`() {
        assertThat(testSubject.part1()).isEqualTo(24)
    }

    @Test
    fun `should count the number of grains of sand before the source is blocked`() {
        assertThat(testSubject.part2()).isEqualTo(93)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(RegolithReservoir(readInputLines(14)).part1())
            .isEqualTo(755)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(RegolithReservoir(readInputLines(14)).part2())
            .isEqualTo(29805)
    }
}
