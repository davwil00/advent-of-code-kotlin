package day25

import utils.readInputLines
import java.io.File
import kotlin.math.exp

class Snowverload(val input: List<String>) {
    private val componentMap = parseInput()

    fun part1(): Int {
        convertToGraphviz()
        val r = kotlin.runCatching {
            val pb = ProcessBuilder("neato", "-Tsvg", "-o graphviz.svg", "graphviz.dot")
            pb.directory(File("src/main/resources/day25"))
            val process = pb.start()
            process.waitFor()
        }
        // inspect by eye
        // cut the wires prk -> gpz, zhg -> qdv, rfq -> lsk
        val cutMap = componentMap.toMutableMap()
        cutMap["prk"] = componentMap.getValue("prk") - "gpz"
        cutMap["zhg"] = componentMap.getValue("zhg") - "qdv"
        cutMap["rfq"] = componentMap.getValue("rfq") - "lsk"

        val biDirectionalMap = buildMap<String, List<String>> {
            // make bidirectional map
            cutMap.entries.forEach { (key, value) ->
                merge(key, value) { l1, l2 -> l1 + l2 }
                value.forEach { component ->
                    merge(component, listOf(key)) { l1, l2 -> l1 + l2}
                }
            }
        }

        // count the nodes linked by prk
        val set1 = explore(biDirectionalMap, setOf("prk"), emptySet())
        // count the nodes linked by gpz
        val set2 = explore(biDirectionalMap, setOf("gpz"), emptySet())

        return set1.size * set2.size
    }

    private tailrec fun explore(map: Map<String, List<String>>, nodesToExplore: Set<String>, exploredNodes: Set<String>): Set<String> {
        if (nodesToExplore.isEmpty()) {
            return exploredNodes
        }
        val node = nodesToExplore.first()
        if (node in map) {
            val nodes = map.getValue(node) - exploredNodes
            return explore(map, nodesToExplore + nodes - node, exploredNodes + node)
        } else {
            return explore(map, nodesToExplore - node, exploredNodes + node)
        }
    }

    private fun parseInput(): Map<String, List<String>> {
        return input.associate { line ->
            val (component, components) = line.split(": ")
            val componentsList = components.split(' ')
            component to componentsList
        }
    }

    private fun convertToGraphviz() {
        val out = File("src/main/resources/day25/graphviz.dot")
        out.writeText("strict graph {\n")
        componentMap.forEach { (component, componentList) ->
            out.appendText("\t$component -- {${componentList.joinToString()}}\n")
        }
        out.appendText("}")
    }
}

fun main() {
    val snowverload = Snowverload(readInputLines(25))
    println(snowverload.part1()) // 550528 too low
    //println(snowverload.part2())
}
