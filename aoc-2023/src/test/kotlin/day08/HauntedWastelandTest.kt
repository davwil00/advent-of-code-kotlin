package day08

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class HauntedWastelandTest {
    private val testInput1 = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)""".lines()

    private val testInput2 = """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)""".lines()

    private val testInput3 = """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)""".lines()

    @Test
    fun `should find number of steps for simple case`() {
        assertThat(HauntedWasteland(testInput1).part1()).isEqualTo(2)
    }

    @Test
    fun `should find number of steps for more complex case`() {
        assertThat(HauntedWasteland(testInput2).part1()).isEqualTo(6)
    }

    @Test
    fun `should find number of steps when traversing simultaneously`() {
        assertThat(HauntedWasteland(testInput3).part2()).isEqualTo(6)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(HauntedWasteland(readInputLines(8)).part1())
            .isEqualTo(20777)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(HauntedWasteland(readInputLines(8)).part2())
            .isEqualTo(13289612809129)
    }
}
