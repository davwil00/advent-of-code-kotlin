package day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class TheFloorWillBeLavaTest {
    private val testInput = """.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|....""".lines()

    private val testSubject = TheFloorWillBeLava(testInput)

    @Test
    fun `should count number of energised tiles from (0,0) heading RIGHT`() {
        assertThat(testSubject.part1()).isEqualTo(46)
    }

    @Test
    fun `should count max number of energised tiles`() {
        assertThat(testSubject.part2()).isEqualTo(51)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(TheFloorWillBeLava(readInputLines(16)).part1())
            .isEqualTo(6902)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(TheFloorWillBeLava(readInputLines(16)).part2())
            .isEqualTo(7697)
    }
}
