package day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.Coordinate
import utils.readInputLines

class BeaconExclusionZoneTest {
    private val testInput = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().lines()

    private val testSubject = BeaconExclusionZone(testInput)

    @Test
    fun `should count exclusion zones in y=10`() {
        assertThat(testSubject.part1(10))
            .isEqualTo(26)
    }

    @Test
    fun `should find coordinates on circle`() {
        val sensor = BeaconExclusionZone.Sensor(Coordinate(8, 7), Coordinate(2, 10))
        println(sensor.getCoordinatesOnCircleWithinBounds(20))
    }

    @Test
    fun `should find coordinate of beacon`() {
        assertThat(testSubject.part2(20))
            .isEqualTo(56000011)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(BeaconExclusionZone(readInputLines(15)).part1())
            .isEqualTo(5809294)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(BeaconExclusionZone(readInputLines(15)).part2())
            .isEqualTo(10693731308112)
    }
}
