package day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInput

class PointsOfIncidenceTest {
    private val testInput = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""

    private val testSubject = PointsOfIncidence(testInput)

    @Test
    fun `should fine the correct lines of reflection in sample input`() {
        assertThat(testSubject.part1()).isEqualTo(405)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(PointsOfIncidence(readInput(13)).part1())
            .isEqualTo(30705)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(PointsOfIncidence(readInputLines(13)).part2())
        //    .isEqualTo()
    }
}
