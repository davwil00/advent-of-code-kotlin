package day22

import org.slf4j.LoggerFactory
import utils.Coordinate3D
import utils.readInputLines
import kotlin.math.max
import kotlin.math.min

class ReactorReboot {

    fun readRebootSteps(input: List<String>): List<RebootStep> {
        return input.map { RebootStep.fromString(it) }
    }

    fun applyInitialisationSteps(rebootSteps: List<RebootStep>): Int {
        val cubesOn = mutableSetOf<Coordinate3D>()

        rebootSteps.map { step ->
            val coordinates = generateCoordinates(step)

            if (step.isOn) {
                cubesOn.addAll(coordinates)
            } else {
                cubesOn.removeAll(coordinates)
            }
        }

        return cubesOn.size
    }

    fun applyInitialisationStepsTrackingAreaOnly(rebootSteps: List<RebootStep>): Long {
        return applyStepsTrackingAreaOnly(rebootSteps
            .filter {
                it.shape.xRange.first >= -50 && it.shape.xRange.last <= 50 &&
                it.shape.yRange.first >= -50 && it.shape.yRange.last <= 50 &&
                it.shape.zRange.first >= -50 && it.shape.zRange.last <= 50
            }
            .map { step ->
            step.copy(shape = Shape(
                max(-50, step.shape.xRange.first)..min(50, step.shape.xRange.last),
                max(-50, step.shape.yRange.first)..min(50, step.shape.yRange.last),
                max(-50, step.shape.zRange.first)..min(50, step.shape.zRange.last),
            ))
        })
    }

    private fun mergeShapes(shape1: Shape, shape2: Shape): List<Shape> {
        if (shape1.overlapsWith(shape2)) {
            val newShape1 = Shape(
                shape2.xRange.first..shape1.xRange.first,
                shape2.yRange.first..shape2.yRange.last,
                shape2.zRange.first..shape2.zRange.last
            )
            val newShape2 = Shape(
                shape1.xRange.last..shape2.xRange.last,
                shape2.yRange.first..shape2.yRange.last,
                shape2.zRange.first..shape2.zRange.last
            )
            val newShape3 = Shape(
                shape1.xRange.first..shape1.xRange.last,
                shape1.yRange.last..shape2.yRange.last,
                shape2.zRange.first..shape2.zRange.last
            )
            val newShape4 = Shape(
                shape1.xRange.first..shape1.xRange.last,
                shape2.yRange.first..shape1.yRange.first,
                shape2.zRange.first..shape2.zRange.last
            )
            val newShape5 = Shape(
                shape1.xRange.first..shape1.xRange.last,
                shape1.yRange.first..shape1.yRange.last,
                shape2.zRange.first..shape1.zRange.first
            )
            val newShape6 = Shape(
                shape1.xRange.first..shape1.xRange.last,
                shape1.yRange.first..shape1.yRange.last,
                shape1.zRange.last..shape2.zRange.last
            )

            return listOf(shape1, newShape1, newShape2, newShape3, newShape4, newShape5, newShape6).filter { it.calculateArea() > 0 }
        } else {
            return listOf(shape1, shape2)
        }
    }

    private fun applyStepsTrackingAreaOnly(rebootSteps: List<RebootStep>): Long {
        var total = 0L
        rebootSteps.forEachIndexed { idx, step ->
            if (step.isOn) {
                val intersections = mutableListOf<Shape>()
                total += step.shape.calculateArea()
                logger.debug("switching on {} cubes, total: {}", step.shape.calculateArea(), total)
                val shapeForIntersecting = step.shape

                /* check to see if it also intersects with any other areas we've already counted
                // *and* if that intersection intersects with any intersection we've already discounted
                // if the instruction was to switch on that section, discount the area
                // if the instruction was to switch off that section, we want to switch it back on again so ignore? */
                rebootSteps
                    .subList(0, idx)
                    .filter { it.isOn }
                    .forEach { otherStep ->
                        if (shapeForIntersecting.overlapsWith(otherStep.shape)) {
                            val intersectionWithOriginalShape = shapeForIntersecting.getOverlap(otherStep.shape)
                            var area = intersectionWithOriginalShape.calculateArea()
                            // check other intersections
                            intersections.forEach { intersectionIntersection ->
                                if (shapeForIntersecting.overlapsWith(intersectionIntersection)) {
                                    area -= shapeForIntersecting.getOverlap(intersectionIntersection).calculateArea()
                                }
                            }
                            if (area > 0) {
                                total -= area
                            }
                            logger.debug("intersected with already switched on area, removing {} cubes, total: {}", area, total)
                            intersections.add(intersectionWithOriginalShape)
                        }
                    }
            } else {
                // ok, we're switching things off now
                // is it just the same but the area gets smaller and then if there's an intersection it makes the area to minus smaller not bigger?
                val intersections = mutableListOf<Shape>()
                total -= step.shape.calculateArea()
                val shapeForIntersecting = step.shape
                rebootSteps
                    .subList(0, idx)
                    .filterNot { it.isOn }
                    .map { it.shape }
                    .forEach { otherShape ->
                        if (step.shape.overlapsWith(otherShape)) {
                            val intersectionWithOriginalShape = step.shape.getOverlap(otherShape)
                            var area = intersectionWithOriginalShape.calculateArea()

                            intersections.forEach { intersectionIntersection ->
                                if (shapeForIntersecting.overlapsWith(intersectionIntersection)) {
                                    area += shapeForIntersecting.getOverlap(intersectionIntersection).calculateArea()
                                }
                            }
                            total += area
                            intersections.add(intersectionWithOriginalShape)
                        }
                    }
            }
        }

        return total
    }

    private fun generateCoordinates(step: RebootStep): Set<Coordinate3D> {
        return step.shape.xRange
            .filter { it >=-50 && it <= 50 }
            .flatMap { x ->
                step.shape.yRange
                    .filter { it >=-50 && it <= 50 }
                    .flatMap { y ->
                        step.shape.zRange
                            .filter { it >=-50 && it <= 50 }
                            .map { z ->
                            Coordinate3D(x, y, z)
                        }
                    }
            }.toSet()
    }

    data class Shape(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

        fun calculateArea(): Long =
            (xRange.last + 1 - xRange.first).toLong() *
                    (yRange.last + 1 - yRange.first).toLong() *
                    (zRange.last + 1 - zRange.first).toLong()

        fun getOverlap(shape: Shape): Shape = Shape(
            max(xRange.first, shape.xRange.first)..min(xRange.last, shape.xRange.last),
            max(yRange.first, shape.yRange.first)..min(yRange.last, shape.yRange.last),
            max(zRange.first, shape.zRange.first)..min(zRange.last, shape.zRange.last)
        )

        fun overlapsWith(shape2: Shape) =
            overlapsWith(xRange, shape2.xRange) &&
            overlapsWith(yRange, shape2.yRange) &&
            overlapsWith(zRange, shape2.zRange)

        private fun overlapsWith(range1: IntRange, range2: IntRange) =
            range2.last in range1 || range1.last in range2
    }

    data class RebootStep(val isOn: Boolean, val shape: Shape) {

        companion object {
            private val regex = Regex("""(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""")

            fun fromString(str: String): RebootStep {
                regex.matchEntire(str)?.let {
                    val values = it.groupValues
                    val state = values[1] == "on"
                    return RebootStep(
                        state,
                        Shape(values[2].toInt()..values[3].toInt(),
                        values[4].toInt()..values[5].toInt(),
                        values[6].toInt()..values[7].toInt())
                    )
                }

                throw IllegalArgumentException("Unable to parse reboot step")
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ReactorReboot::class.java)
    }
}

fun main() {
    val input = readInputLines(22)
    val reactorReboot = ReactorReboot()
    val steps = reactorReboot.readRebootSteps(input)
    val noOfCubesOn = reactorReboot.applyInitialisationSteps(steps)
    println("Number of cubes switched on is: $noOfCubesOn")
}
