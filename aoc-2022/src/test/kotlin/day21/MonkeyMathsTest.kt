package day21

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class MonkeyMathsTest {
    private val testInput = """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent().lines()

    private val testSubject = MonkeyMaths(testInput)

    @Test
    fun `should find number root monkey yells`() {
        assertThat(testSubject.part1())
            .isEqualTo(152)
    }

    @Test
    fun `should find target value`() {
        assertThat(testSubject.part2()).isEqualTo(301)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(MonkeyMaths(readInputLines(21)).part1())
            .isEqualTo(78342931359552)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(MonkeyMaths(readInputLines(21)).part2())
            .isEqualTo(3296135418820)
    }
}
