package day04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class CampCleanupTest {
    private val testInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent().lines()

    private val testSubject = CampCleanup(testInput)

    @Test
    fun `should find completely overlapping pairs`() {
        assertThat(testSubject.findCompletelyOverlappingPairs())
            .isEqualTo(2)
    }

    @Test
    fun `should find overlapping pairs`() {
        assertThat(testSubject.findOverlappingPairs())
            .isEqualTo(4)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(CampCleanup(readInputLines(4)).findCompletelyOverlappingPairs())
            .isEqualTo(536)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(CampCleanup(readInputLines(4)).findOverlappingPairs())
            .isEqualTo(845)
    }
}
