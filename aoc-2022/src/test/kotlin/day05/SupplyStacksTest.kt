package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class SupplyStacksTest {
    private val testInput = """
        |    [D]
        |[N] [C]
        |[Z] [M] [P]
        | 1   2   3
        |
        |move 1 from 2 to 1
        |move 3 from 1 to 3
        |move 2 from 2 to 1
        |move 1 from 1 to 2
    """.trimMargin().lines()

    private val testSubject = SupplyStacks(testInput)

    @Test
    fun `should correctly arrange stacks`() {
        assertThat(testSubject.part1())
            .isEqualTo("CMZ")
    }

    @Test
    fun `should correctly arrange stacks part 2`() {
        assertThat(testSubject.part2())
            .isEqualTo("MCD")
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(SupplyStacks(File("src/main/resources/day05/input.txt").readText().lines()).part1())
            .isEqualTo("ZSQVCCJLL")
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(SupplyStacks(File("src/main/resources/day05/input.txt").readText().lines()).part2())
            .isEqualTo("QZFJRWHGS")
    }
}
