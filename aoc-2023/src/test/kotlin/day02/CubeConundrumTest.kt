package day02

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class CubeConundrumTest {
    private val testInput = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".lines()

    private val testSubject = CubeConundrum(testInput)

    @Test
    fun `should get correct answer for test input`() {
        assertThat(testSubject.part1()).isEqualTo(8)
        assertThat(testSubject.part2()).isEqualTo(2286)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(CubeConundrum(readInputLines(2)).part1())
            .isEqualTo(2265)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(CubeConundrum(readInputLines(2)).part2())
            .isEqualTo(64097)
    }
}
