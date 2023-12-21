package day20

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@Suppress("DANGEROUS_CHARACTERS")
class PulsePropagationTest {

    private val simpleTestInput = """broadcaster -> a, b, c
%a -> b
%b -> c
%c -> inv
&inv -> a""".lines()

    private val testInput = """broadcaster -> a
%a -> inv, con
&inv -> b
%b -> con
&con -> output""".lines()

    private val testSubject = PulsePropagation(testInput)

    @Test
    fun `should calculate number of high * number of low states sent for simple case`() {
        assertThat(PulsePropagation(simpleTestInput).part1()).isEqualTo(32000000)
    }

    @Test
    fun `should calculate number of high * number of low states sent`() {
        assertThat(testSubject.part1()).isEqualTo(11687500)
    }

    @Test
    fun `should work for new test case`() {
        assertThat(PulsePropagation("""broadcaster -> a, b
%a -> c
%b -> c
&c -> output""".lines()).part1()).isEqualTo(11250000)
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(PulsePropagation(readInputLines(20)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(PulsePropagation(readInputLines(20)).part2())
        //    .isEqualTo()
    }
}
