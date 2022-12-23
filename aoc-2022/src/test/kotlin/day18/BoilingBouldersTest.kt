package day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class BoilingBouldersTest {
    private val testInput = """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent().lines()

    private val testSubject = BoilingBoulders(testInput)

    @Test
    fun `should count faces not connected`() {
        assertThat(testSubject.part1()).isEqualTo(64)
    }

//    @Test
//    fun `should count exposed faces`() {
//        assertThat(testSubject.part2()).isEqualTo(58)
//    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(BoilingBoulders(readInputLines(18)).part1())
            .isEqualTo(3526)
    }

//    @Test
//    fun `should get correct answer for part 2`() {
//         assertThat(BoilingBoulders(readInputLines(18)).part2())
//            .isEqualTo(1)
//    }
}
