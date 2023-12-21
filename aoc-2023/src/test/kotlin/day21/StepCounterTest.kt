package day21

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class StepCounterTest {
    private val testInput = """...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
...........""".lines()

    private val testSubject = StepCounter(testInput, 6)

    @Test
    fun `should find number of possible locations after 16 steps`() {
        assertThat(testSubject.part1()).isEqualTo(16)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(StepCounter(readInputLines(21), 64).part1())
            .isEqualTo(3598)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(StepCounter(readInputLines(21)).part2())
        //    .isEqualTo()
    }
}
