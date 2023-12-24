package day23

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class ALongWalkTest {
    private val testInput = """#.#####################
#.......#########...###
#######.#########.#.###
###.....#.>.>.###.#.###
###v#####.#v#.###.#.###
###.>...#.#.#.....#...#
###v###.#.#.#########.#
###...#.#.#.......#...#
#####.#.#.#######.#.###
#.....#.#.#.......#...#
#.#####.#.#.#########v#
#.#...#...#...###...>.#
#.#.#v#######v###.###v#
#...#.>.#...>.>.#.###.#
#####v#.#.###v#.#.###.#
#.....#...#...#.#.#...#
#.#########.###.#.#.###
#...###...#...#...#.###
###.###.#.###v#####v###
#...#...#.#.>.>.#.>.###
#.###.###.#.###.#.#v###
#.....###...###...#...#
#####################.#""".lines()

    private val testSubject = ALongWalk(testInput)

    @Test
    fun `should find the length of the longest path with slippery paths`() {
        assertThat(testSubject.part1()).isEqualTo(94)
    }

    @Test
    fun `should find the length of the longest path without slippery paths`() {
        assertThat(testSubject.part2()).isEqualTo(154)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(ALongWalk(readInputLines(23)).part1())
            .isEqualTo(1998)
    }

    @Test
    fun `should get correct answer for part 2`() {
//         assertThat(ALongWalk(readInputLines(23)).part2())
//            .isEqualTo()
    }
}
