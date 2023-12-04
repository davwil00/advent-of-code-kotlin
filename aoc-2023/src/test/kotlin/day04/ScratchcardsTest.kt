package day04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class ScratchcardsTest {
    private val testInput = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""".lines()

    private val testSubject = Scratchcards(testInput)

    @Test
    fun `should calculate the number of points`() {
        assertThat(testSubject.part1()).isEqualTo(13)
    }

    @Test
    fun `should calculate total scratchcards`() {
        assertThat(testSubject.part2()).isEqualTo(30)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(Scratchcards(readInputLines(4)).part1())
            .isEqualTo(20667)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(Scratchcards(readInputLines(4)).part2())
            .isEqualTo(5833065)
    }
}
