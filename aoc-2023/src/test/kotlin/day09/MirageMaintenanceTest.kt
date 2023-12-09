package day09

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class MirageMaintenanceTest {
    private val testInput = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45""".lines()

    private val testSubject = MirageMaintenance(testInput)

    @Test
    fun `should calculate sum of the next values in the sequence`() {
        assertThat(testSubject.part1()).isEqualTo(114)
    }

    @Test
    fun `should calculate the sum of the previous values in the sequence`() {
        assertThat(testSubject.part2()).isEqualTo(2)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(MirageMaintenance(readInputLines(9)).part1())
            .isEqualTo(1974913025)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(MirageMaintenance(readInputLines(9)).part2())
            .isEqualTo(884)
    }
}
