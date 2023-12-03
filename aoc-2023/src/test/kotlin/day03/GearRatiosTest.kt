package day03

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class GearRatiosTest {
    private val testInput = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598..""".lines()

    private val testInput2 = """12.......*..
+.........34
.......-12..
..78........
..*....60...
78..........
.......23...
....90*12...
............
2.2......12.
.*.........*
1.1.......56""".lines()

    private val testSubject = GearRatios(testInput)

    @Test
    fun `should find sum of all numbers adjacent to a symbol`() {
        assertThat(testSubject.part1()).isEqualTo(4361)
    }

    @Test
    fun `should find sum of all numbers adjacent to a symbol for test input 2`() {
        assertThat(GearRatios(testInput2).part1()).isEqualTo(413)
    }

    @Test
    fun `should find sum of gear ratios`() {
        assertThat(testSubject.part2()).isEqualTo(467835)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(GearRatios(readInputLines(3)).part1())
            .isEqualTo(532445)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(GearRatios(readInputLines(3)).part2())
            .isEqualTo(79842967)
    }
}
