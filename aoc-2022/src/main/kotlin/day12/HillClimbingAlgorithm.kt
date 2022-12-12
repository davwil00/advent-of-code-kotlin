package day12

import utils.Coordinate
import utils.Edge
import utils.Graph
import utils.readInputLines

class HillClimbingAlgorithm(input: List<String>) {

    private val map = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, value ->
            Coordinate(x, y) to value
        }
    }.toMap()

    private val startingCoordinate = map.entries.first { it.value == 'S' }.key
    private val endingCoordinate = map.entries.first { it.value == 'E' }.key

    private fun produceEdgeList(): List<Edge<String>> {
        return map.flatMap { (node, value) ->
            node.getAdjacentCoordinates()
                .filter { it in map }
                .filter {
                    val valueAtCoordinate = map.getValue(it)
                    when (value) {
                    'S' -> valueAtCoordinate == 'a'
                    'E' -> false
                    'z' -> valueAtCoordinate == 'E' || valueAtCoordinate <= value
                    else -> valueAtCoordinate.isLowerCase() && valueAtCoordinate <= value + 1
                }}
                .map { coordinate -> Edge(node.toString(), coordinate.toString(), 1) }
        }
    }

    fun part1(): Int {
        val edges = produceEdgeList()
        val graph = Graph(edges, true)
        graph.dijkstra(startingCoordinate.toString())
        return graph.getWeightToPath(endingCoordinate.toString())
    }

    fun part2(): Int? {
        val edges = produceEdgeList()
        val graph = Graph(edges, true)

        return map.entries.filter { (_, value) ->
            value == 'a'
        }.minOfOrNull {
            graph.dijkstra(it.key.toString())
            graph.getWeightToPath(endingCoordinate.toString())
        }
    }
}

fun main() {
    val hillClimbingAlgorithm = HillClimbingAlgorithm(readInputLines(12))
    println(hillClimbingAlgorithm.part1())
    println(hillClimbingAlgorithm.part2())
}
