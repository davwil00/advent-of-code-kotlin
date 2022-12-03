package day03

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class RucksackReorganisationTest {
    private val testInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().lines()

    private val testSubject = RucksackReorganisation(testInput)

    @Test
    fun `should find overlapping items`() {
        assertThat(testSubject.rucksackContents.map{testSubject.findOverlappingItems(it)})
            .containsExactly('p', 'L', 'P', 'v', 't', 's')
    }

    @Test
    fun `should calculate priority`() {
        assertThat(testSubject.getPriority('a')).isEqualTo(1)
        assertThat(testSubject.getPriority('A')).isEqualTo(27)
    }

    @Test
    fun `should identify common items`() {
        val rucksackContents = testSubject.rucksackContents
        assertThat(testSubject.findCommonItem(rucksackContents[0], rucksackContents[1], rucksackContents[2]))
            .isEqualTo('r')
        assertThat(testSubject.findCommonItem(rucksackContents[3], rucksackContents[4], rucksackContents[5]))
            .isEqualTo('Z')
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(RucksackReorganisation(readInputLines(3)).part1())
            .isEqualTo(7850)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(RucksackReorganisation(readInputLines(3)).part2())
            .isEqualTo(2581)
    }
}
