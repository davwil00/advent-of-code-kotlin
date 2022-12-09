package day09

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class RopeBridgeTest {
    private val testInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().lines()

    private val testSubject = RopeBridge(testInput)

    @Test
    fun `should count number of unique tail positions`() {
        assertThat(testSubject.part1())
            .isEqualTo(13)
    }

    @Test
    fun `should count number of unique tail positions for 9th knot`() {
        assertThat(testSubject.part2())
            .isEqualTo(1)
    }

    @Test
    fun `should count number of unique tail positions for 9th knot - larger example`() {
        val largerExample = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent()
        val ropeBridge = RopeBridge(largerExample.lines())
        assertThat(ropeBridge.part2())
            .isEqualTo(36)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(RopeBridge(readInputLines(9)).part1())
            .isEqualTo(6190)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(RopeBridge(readInputLines(9)).part2())
            .isEqualTo(2516)
    }
}
