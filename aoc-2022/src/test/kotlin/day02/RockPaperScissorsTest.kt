package day02

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

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


    @Test
    fun `should get correct answer for part 1`() {
        assertThat(RockPaperScissors(readInputLines(2)).calculateScore())
            .isEqualTo(12156)
    }

    @Test
    fun `should get correct answer for part 2`() {
        assertThat(RockPaperScissors(readInputLines(2)).calculateScoreForDesiredOutcome())
            .isEqualTo(10835)
    }
}
