package day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PyroclasticFlowTest {
    private val testInput = """>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"""

    private val testSubject = PyroclasticFlow(testInput)

    @Test
    fun `should count height`() {
        assertThat(testSubject.part1()).isEqualTo(3068)
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(PyroclasticFlow(readInputLines(17)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(PyroclasticFlow(readInputLines(17)).part2())
        //    .isEqualTo()
    }
}
