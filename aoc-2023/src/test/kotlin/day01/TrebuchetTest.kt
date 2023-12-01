package day01

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.readInputLines

class TrebuchetTest {

    private val testSubject = Trebuchet()

    @ParameterizedTest
    @CsvSource("1abc2,12", "pqr3stu8vwx,38", "a1b2c3d4e5f,15", "treb7uchet,77")
    fun `should extract first and last numbers from input`(input: String, expected: Long) {
        assertThat(testSubject.extractFirstAndLastNumbers(input)).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        "two1nine,2",
        "eightwothree,8",
        "abcone2threexyz,1",
        "xtwone3four,2",
        "4nineeightseven2,4",
        "zoneight234,1",
        "7pqrstsixteen,7"
    )
    fun `should find first digit or word`(input: String, expected: Int) {
        assertThat(testSubject.findFirstDigitOrWord(input)).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        "two1nine,9",
        "eightwothree,3",
        "abcone2threexyz,3",
        "xtwone3four,4",
        "4nineeightseven2,2",
        "zoneight234,4",
        "7pqrstsixteen,6"
    )
    fun `should find last digit or word`(input: String, expected: Int) {
        assertThat(testSubject.findLastDigitOrWord(input)).isEqualTo(expected)
    }

    @Test
    fun `part2 test sum`() {
        assertThat(testSubject.part2("""two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen""".lines())).isEqualTo(281)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(Trebuchet().part1(readInputLines(1)))
            .isEqualTo(55002)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(Trebuchet().part2(readInputLines(1)))
            .isEqualTo(55093)
    }
}
