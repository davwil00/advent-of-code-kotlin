package day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class CosmicExpansionTest {
    private val testInput = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....""".lines()

    private val testSubject = CosmicExpansion(testInput)

    @Test
    fun `should find sum of distances between galaxies in expanded universe`() {
        assertThat(testSubject.part1()).isEqualTo(374)
    }

    @Test
    fun `should find sum of distances between galaxies in super expanded universe`() {
        assertThat(testSubject.part2(10)).isEqualTo(1030)
        assertThat(testSubject.part2(100)).isEqualTo(8410)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(CosmicExpansion(readInputLines(11)).part1())
            .isEqualTo(9639160)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(CosmicExpansion(readInputLines(11)).part2())
            .isEqualTo(752936133304)
    }
}
