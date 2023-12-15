package day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LensLibraryTest {
    private val testInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

    private val testSubject = LensLibrary(testInput)

    @Test
    fun `should correctly calculate the hash code for 'HASH'`() {
        assertThat(testSubject.hashCode("HASH")).isEqualTo(52)
    }

    @Test
    fun `should correctly calculate the sum of the hash codes for the test input`() {
        assertThat(testSubject.part1()).isEqualTo(1320)
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(LensLibrary(readInputLines(15)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(LensLibrary(readInputLines(15)).part2())
        //    .isEqualTo()
    }
}
