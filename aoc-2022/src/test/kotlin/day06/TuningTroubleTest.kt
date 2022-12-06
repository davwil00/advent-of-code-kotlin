package day06

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInput

class TuningTroubleTest {
    private val testInput = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"

    private val testSubject = TuningTrouble(testInput)

    @Test
    fun `should find index of start of packet marker`() {
        assertThat(testSubject.part1()).isEqualTo(7)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(TuningTrouble(readInput(6)).part1())
            .isEqualTo(1723)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(TuningTrouble(readInput(6)).part2())
            .isEqualTo(3708)
    }
}
