package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInput

class IfYouGiveASeedAFertilizerTest {
    private val testInput = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""

    private val testSubject = IfYouGiveASeedAFertilizer(testInput)

    @Test
    fun `should find smallest location`() {
        assertThat(testSubject.part1()).isEqualTo(35)
    }

    @Test
    fun `should find smallest location for part 2`() {
        assertThat(testSubject.part2()).isEqualTo(46)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(IfYouGiveASeedAFertilizer(readInput(5)).part1())
            .isEqualTo(218513636)
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(IfYourGiveASeedAFertilizer(readInputLines(5)).part2())
        //    .isEqualTo()
    }
}
