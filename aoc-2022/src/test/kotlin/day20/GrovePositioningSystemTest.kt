package day20

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class GrovePositioningSystemTest {
    private val testInput = """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent().lines()

    private val testSubject = GrovePositioningSystem(testInput)

    @Test
    fun `should find grove coordinates`() {
        assertThat(testSubject.part1()).isEqualTo(3)
    }

    @Test
    fun `should find big grove coordinates`() {
        assertThat(testSubject.part2()).isEqualTo(1623178306)
    }

    @Test
    fun `should normalise negative indexes`() {
        assertThat(testSubject.normalise(-1))
            .isEqualTo(5)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(GrovePositioningSystem(readInputLines(20)).part1())
            .isEqualTo(7153)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(GrovePositioningSystem(readInputLines(20)).part2())
        //    .isEqualTo()
    }
}
