package day20

import org.slf4j.LoggerFactory
import utils.readInputLines
import java.util.*
import kotlin.math.abs
import kotlin.math.floor

class GrovePositioningSystem(input: List<String>) {
    private val fileContent = input.mapIndexed { idx, value -> ListEntry(idx, value.toLong()) }
    private val logger = LoggerFactory.getLogger(javaClass)

    fun part1(): Long {
        val decryptedList = fileContent.toCollection(LinkedList())
        mix(fileContent, decryptedList)

        val indexOf0 = decryptedList.indexOfFirst { it.value == 0L }
        return listOf(1000, 2000, 3000).sumOf {
            decryptedList[(indexOf0 + it) % fileContent.size].value
        }
    }

    fun part2(): Long {
        val originalList = fileContent
            .map { entry -> ListEntry(entry.originalIdx, entry.value * 811589153L) }
        val decryptedList = originalList
            .toCollection(LinkedList())
        repeat(10) {
            mix(originalList, decryptedList)
        }
        val indexOf0 = decryptedList.indexOfFirst { it.value == 0L }
        return listOf(1000, 2000, 3000).sumOf {
            decryptedList[(indexOf0 + it) % fileContent.size].value
        }
    }

    private fun mix(originalList: List<ListEntry>, decryptedList: LinkedList<ListEntry>) {
        originalList.forEach { entry ->
            val idx = decryptedList.indexOf(entry)
            decryptedList.removeAt(idx)
            val newIndex = normalise(idx + entry.value)
            decryptedList.add(newIndex, entry)
            if (originalList.size < 10) logger.debug(decryptedList.joinToString())
        }
    }

    fun normalise(index: Long): Int {
        val listSize = fileContent.size - 1
        return when {
            index > listSize -> (index % listSize).toInt()
            index < 0 -> (abs(floor(index.toDouble() / listSize).toLong() * listSize) + index).toInt()
            else -> index.toInt()
        }
    }

    data class ListEntry(val originalIdx: Int, val value: Long)
}

fun main() {
    val grovePositioningSystem = GrovePositioningSystem(readInputLines(20))
    println(grovePositioningSystem.part1())
    println(grovePositioningSystem.part2())
}
