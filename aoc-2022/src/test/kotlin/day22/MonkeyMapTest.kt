package day22

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class MonkeyMapTest {
    private val testInput = """        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5"""

    private val testSubject = MonkeyMap(testInput)

    @Test
    fun `should find password`() {
        assertThat(testSubject.part1())
            .isEqualTo(6032)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(MonkeyMap(File("src/main/resources/day22/input.txt").readText().trimEnd()).part1())
            .isEqualTo(58248)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(File("src/main/resources/day22/input.txt").readText().trimEnd()).part2())
        //    .isEqualTo()
    }
}
