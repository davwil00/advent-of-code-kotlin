package day02

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RockPaperScissorsTest {

    private val testInput = """A Y
                              |B X
                              |C Z""".trimMargin().lines()

    private val testSubject = RockPaperScissors(testInput)

    @Test
    fun `should calculate correct score`() {
        assertThat(testSubject.calculateScore()).isEqualTo(15)
    }

    @Test
    fun `should calculate correct score based on outcome`() {
        assertThat(testSubject.calculateScoreForDesiredOutcome()).isEqualTo(12)
    }
}
