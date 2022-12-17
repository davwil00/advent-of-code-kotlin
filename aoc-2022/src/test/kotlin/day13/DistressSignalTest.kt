package day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.readInput

class DistressSignalTest {
    private val testInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]

    """.trimIndent()

    private val testSubject = DistressSignal(testInput)

    @Test
    fun `should parse simple packet`() {
        assertThat(testSubject.parsePacket("[1,1,3,1,1]").toString())
            .isEqualTo("[1,1,3,1,1]")
    }

    @Test
    fun `should parse empty packet`() {
        assertThat(testSubject.parsePacket("[]").toString())
            .isEqualTo("[]")
    }

    @Test
    fun `should parse complex packet`() {
        assertThat(testSubject.parsePacket("[1,[2,[3,[4,[5,6,7]]]],8,9]").toString())
            .isEqualTo("[1,[2,[3,[4,[5,6,7]]]],8,9]")
    }

    @ParameterizedTest
    @CsvSource(value = [
        "[1,1,3,1,1]|[1,1,5,1,1]|true",
        "[[1],[2,3,4]]|[[1],4]|true",
        "[9]|[[8,7,6]]|false",
        "[[4,4],4,4]|[[4,4],4,4,4]|true",
        "[7,7,7,7]|[7,7,7]|false",
        "[]|[3]|true",
        "[[[]]]|[[]]|false",
        "[1,[2,[3,[4,[5,6,7]]]],8,9]|[1,[2,[3,[4,[5,6,0]]]],8,9]|false",
        "[[8,[[7,10,10,5],[8,4,9]],3,5],[[[3,9,4],5,[7,5,5]],[[3,2,5],[10],[5,5],0,[8]]],[4,2,[],[[7,5,6,3,0],[4,4,10,7],6,[8,10,9]]],[[4,[],4],10,1]]|[[[[8],[3,10],[7,6,3,7,4],1,8]]]|true",
        "[[8,[[7]]]]|[[[[8]]]]|false",
        "[[1,2],4]|[[1],5,5]|false",
        "[[1,2],4]|[[3],5,5]|true",
        "[[2,8,[4,[10],3],[],[2]],[0,[[3,0],[5,10,10]],[[10,1],[10,6,5,9,3]]],[],[],[[]]]|[[[10],[[],6,1,7]],[],[[[7],[3,10,8,7]]]]|true",
    ], delimiter = '|')
    fun `should identify if packets are in order`(packet1: String, packet2: String, expected: Boolean) {
        val expectedRes = if (expected) -1 else 1
        assertThat(testSubject.parsePacket(packet1).compareTo(testSubject.parsePacket(packet2)))
            .isEqualTo(expectedRes)
    }

    @Test
    fun `should order test input with dividers`() {
        val inputPackets = testInput
            .lines()
            .filterNot { it.isBlank() }
            .map { testSubject.parsePacket(it) }
            .toMutableList()
        val dividerPacket1 = testSubject.parsePacket("[[2]]")
        val dividerPacket2 = testSubject.parsePacket("[[6]]")
        inputPackets.add(dividerPacket1)
        inputPackets.add(dividerPacket2)
        inputPackets.sort()

        val idx1 = inputPackets.indexOf(dividerPacket1) + 1
        val idx2 = inputPackets.indexOf(dividerPacket2) + 1
        assertThat(idx1 * idx2).isEqualTo(140)
    }

    @Test
    fun part1() {
        assertThat(testSubject.part1())
            .isEqualTo(13)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(DistressSignal(readInput(13)).part1())
            .isEqualTo(5580)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(DistressSignal(readInput(13)).part2())
            .isEqualTo(26200)
    }
}
