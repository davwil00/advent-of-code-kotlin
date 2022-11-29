package day22

import day22.ReactorReboot.RebootStep
import day22.ReactorReboot.Shape
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readTestInputLines

class ReactorRebootTest {

    private val testInput = """on x=10..12,y=10..12,z=10..12
on x=11..13,y=11..13,z=11..13
off x=9..11,y=9..11,z=9..11
on x=10..10,y=10..10,z=10..10""".lines()

    private val reactorReboot = ReactorReboot()

	@Test
    fun `switch on small range`() {
        val steps = reactorReboot.readRebootSteps(testInput)
        assertThat(reactorReboot.applyInitialisationSteps(steps)).isEqualTo(39)
    }

	@Test
    fun `switch on small range area`() {
        val steps = reactorReboot.readRebootSteps(testInput)
        assertThat(reactorReboot.applyInitialisationStepsTrackingAreaOnly(steps)).isEqualTo(39)
    }

    @Test
    fun `switch on larger range`() {
        val steps = reactorReboot.readRebootSteps(readTestInputLines(22, "input-1.txt"))
        assertThat(reactorReboot.applyInitialisationStepsTrackingAreaOnly(steps)).isEqualTo(590784)
    }

    @Test
    fun `calculate size of small list 1D list by tracking intersections`() {
        val steps = listOf(
            RebootStep(true, Shape(0..4, 0..0, 0..0)),
            RebootStep(true, Shape(2..7, 0..0, 0..0)),
            RebootStep(true, Shape(1..3, 0..0, 0..0)),
        )
        val size = reactorReboot.applyInitialisationStepsTrackingAreaOnly(steps)
        assertThat(size).isEqualTo(8)
    }
}
