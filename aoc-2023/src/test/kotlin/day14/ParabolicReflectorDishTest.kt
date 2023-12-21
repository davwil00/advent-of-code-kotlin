package day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class ParabolicReflectorDishTest {
    private val testInput = """
        |O....#....
        |O.OO#....#
        |.....##...
        |OO.#O....O
        |.O.....O#.
        |O.#..O.#.#
        |..O..#O..O
        |.......O..
        |#....###..
        |#OO..#....""".trimMargin().lines()

    private val testSubject = ParabolicReflectorDish(testInput)

    @Test
    fun `should calculate total load on north support beams`() {
        assertThat(testSubject.part1()).isEqualTo(136)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(ParabolicReflectorDish(readInputLines(14)).part1())
            .isEqualTo(105003)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(ParabolicReflectorDish(readInputLines(14)).part2())
        //    .isEqualTo()
    }
}
