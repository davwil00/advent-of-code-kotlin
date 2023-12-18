package day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LavaductLagoonTest {
    private val testInput = """R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)""".lines()

    private val testSubject = LavaductLagoon(testInput)

    @Test
    fun `should find total amount of lava`() {
        assertThat(testSubject.part1()).isEqualTo(62)
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(LavaductLagoon(readInputLines(18)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(LavaductLagoon(readInputLines(18)).part2())
        //    .isEqualTo()
    }
}
