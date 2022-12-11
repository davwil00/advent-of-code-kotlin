package day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInput

class MonkeyInTheMiddleTest {
    private val testInput = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3

        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0

        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3

        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent()

    private val testSubject = MonkeyInTheMiddle(testInput)

    @Test
    fun `should calculate monkey business for 20 rounds`() {
        assertThat(testSubject.part1())
            .isEqualTo(10605)
    }

    @Test
    fun `should calculate monkey business for 10000 rounds`() {
        assertThat(testSubject.part2())
            .isEqualTo(2713310158)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(MonkeyInTheMiddle(readInput(11)).part1())
            .isEqualTo(102399)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(MonkeyInTheMiddle(readInput(11)).part2())
            .isEqualTo(23641658401)
    }
}
