package day25

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.readInputLines

class FullOfHotAirTest {
    private val testInput = """
        1=-0-2
        12111
        2=0=
        21
        2=01
        111
        20012
        112
        1=-1=
        1-12
        12
        1=
        122
    """.trimIndent().lines()

    private val testSubject = FullOfHotAir(testInput)

    @Test
    fun `should find sum`() {
        assertThat(testSubject.part1())
            .isEqualTo("2=-1=0")
    }

    @ParameterizedTest
    @CsvSource(value = [
        "4890,2=-1=0",
        "5210,2=2=20",
        "31915503502222,2=2-1-010==-0-1-=--2"
    ])
    fun `should convert to SNAFU`(number: Long, expected: String) {
        assertThat(number.toSNAFU()).isEqualTo(expected)
    }

    @Test
    fun `should convert to decimal`() {
        assertThat("2=2-1-010==-0-1-=--2".toDecimal())
            .isEqualTo(31915503502222)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(FullOfHotAir(readInputLines(25)).part1())
            .isEqualTo("2=2-1-010==-0-1-=--2")
    }
}
