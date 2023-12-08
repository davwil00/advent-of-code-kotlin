package day08

import utils.lcm
import utils.readInputLines
import utils.splitToString

class HauntedWasteland(input: List<String>) {
    private val lrInstructions = input[0].splitToString()
    private val nodeMap = parseInput(input)

    fun part1() = traverseMap()

    fun part2() = nodeMap.keys
            .filter { it.endsWith("A") }
            .map { findPeriodicity(it) }
            .lcm()

    private tailrec fun traverseMap(currentNode: String = "AAA", nodesVisited: Int = 0): Int {
        if (currentNode == "ZZZ") {
            return nodesVisited
        }

        val instruction = lrInstructions[nodesVisited % lrInstructions.size]
        val nextNode = if (instruction == "L") nodeMap.getValue(currentNode).first else nodeMap.getValue(currentNode).second
        return traverseMap(nextNode, nodesVisited + 1)
    }

    private tailrec fun findPeriodicity(currentNode: String, currentIndex: Int = 0, foundPeriods: Set<Int> = emptySet()): Long {
        val (nextIndex, nextNode) = traverseMapEndingWithZ(currentNode, currentIndex)
        val period = nextIndex - currentIndex
        if (foundPeriods.contains(period)) {
            return period.toLong()
        }

        return findPeriodicity(nextNode, nextIndex, foundPeriods + period)
    }

    private tailrec fun traverseMapEndingWithZ(currentNode: String, nodesVisited: Int = 0): Pair<Int, String> {
        val instruction = lrInstructions[nodesVisited % lrInstructions.size]
        val nextNode = if (instruction == "L") nodeMap.getValue(currentNode).first else nodeMap.getValue(currentNode).second
        if (currentNode.endsWith("Z")) {
            return Pair(nodesVisited + 1, nextNode)
        }
        return traverseMapEndingWithZ(nextNode, nodesVisited + 1)
    }

    private fun parseInput(input: List<String>): Map<String, NodeLinks> =
        buildMap {
            input
                .drop(2)
                .map { line ->
                    val result = inputRegex.find(line)!!
                    val (key, left, right) = result.destructured
                    put(key, NodeLinks(left, right))
                }
        }

    companion object {
        private val inputRegex = Regex("""(\w{3}) = \((\w{3}), (\w{3})\)""")
    }
}

typealias NodeLinks = Pair<String, String>

fun main() {
    val hauntedWasteland = HauntedWasteland(readInputLines(8))
    println(hauntedWasteland.part1())
    println(hauntedWasteland.part2())
}
