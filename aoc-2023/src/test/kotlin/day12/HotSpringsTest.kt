package day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HotSpringsTest {
    private val testInput = """???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1""".lines()

    private val testSubject = HotSprings(testInput)

    @Test
    fun `should find number of valid combinations in test input`() {
        assertThat(testSubject.part1()).isEqualTo(21)
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(HotSprings(readInputLines(12)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(HotSprings(readInputLines(12)).part2())
        //    .isEqualTo()
    }
}
