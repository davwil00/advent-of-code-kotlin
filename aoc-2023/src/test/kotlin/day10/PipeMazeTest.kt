package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class PipeMazeTest {
    private val simpleLoop = """.....
.S-7.
.|.|.
.L-J.
.....""".lines()

    private val complexLoop = """..F7.
.FJ|.
SJ.L7
|F--J
LJ...""".lines()

    private val pt2LoopExample1 = """...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
...........""".lines()

    @Test
    fun `should find max distance for simple loop`() {
        assertThat(PipeMaze(simpleLoop).part1()).isEqualTo(4)
    }

    @Test
    fun `should find max distance for complex loop`() {
        assertThat(PipeMaze(complexLoop).part1()).isEqualTo(8)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(PipeMaze(readInputLines(10)).part1())
            .isEqualTo(7066)
    }

    @Test
    fun `should find the number of enclosed tiles in pt2 loop example 1`() {
//        assertThat(PipeMaze(pt2LoopExample1).part2()).isEqualTo(4)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(PipeMaze(readInputLines(10)).part2())
        //    .isEqualTo()
    }
}
