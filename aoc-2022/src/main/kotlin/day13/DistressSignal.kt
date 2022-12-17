package day13

import utils.asInt
import utils.readInput

class DistressSignal(val input: String) {
    fun parsePacket(unprocessedPacketData: String): ListPacketData {
        val stack = mutableListOf<ListPacketData>()
        var currentPacket: ListPacketData? = null
        var idx = 0
        while (idx < unprocessedPacketData.length) {
            val char = unprocessedPacketData[idx]
            when {
                char.isDigit() -> {
                    val next = unprocessedPacketData[idx+1] // handle 2 char digits
                    val value = if (next.isDigit()) {
                        idx++
                        (char.toString() + next.toString()).toInt()
                    } else {
                        char.asInt()
                    }
                    currentPacket!!.data.add(IntegerPacketData(value))
                }
                char == '[' -> {
                    currentPacket?.let {
                        stack.add(it)
                    }
                    currentPacket = ListPacketData(mutableListOf())
                }

                char == ']' -> {
                    if (stack.isNotEmpty()) {
                        stack.last().data.add(currentPacket!!)
                        currentPacket = stack.removeLast()
                    }
                }
            }
            idx++
        }

        return currentPacket!!
    }

    fun part1(): Int {
        val packetPairs = input.split("\n\n")
            .map {
                val (pair1, pair2) = it.lines()
                parsePacket(pair1) to parsePacket(pair2)
            }
        return packetPairs
            .mapIndexed { idx, (first, second) ->
                idx + 1 to first.compareTo(second)
            }
            .filter { it.second == -1 }
            .sumOf { it.first }
    }

    fun part2(): Int {
        val inputPackets = input.lines()
            .filterNot { it.isBlank() }
            .map { parsePacket(it) }
            .toMutableList()

        val dividerPacket1 = parsePacket("[[2]]")
        val dividerPacket2 = parsePacket("[[6]]")
        inputPackets.add(dividerPacket1)
        inputPackets.add(dividerPacket2)
        inputPackets.sort()

        val idx1 = inputPackets.indexOf(dividerPacket1) + 1
        val idx2 = inputPackets.indexOf(dividerPacket2) + 1
        return idx1 * idx2
    }


    sealed interface PacketData: Comparable<PacketData> {
        override fun compareTo(other: PacketData): Int {
            return when {
                this is IntegerPacketData && other is IntegerPacketData -> {
                    when {
                        this.value < other.value -> -1
                        this.value == other.value -> 0
                        else -> 1
                    }
                }
                this is ListPacketData && other is ListPacketData -> {
                    for ((first, second) in this.data.zip(other.data)) {
                        return when (val comparisonResult = first.compareTo(second)) {
                            0 -> continue
                            else -> comparisonResult
                        }
                    }
                    return when {
                        this.data.size < other.data.size -> -1
                        this.data.size == other.data.size -> 0
                        else -> 1
                    }
                }
                this is IntegerPacketData -> ListPacketData(mutableListOf(this)).compareTo(other)
                else -> this.compareTo(ListPacketData(mutableListOf(other)))
            }
        }
    }

    class IntegerPacketData(val value: Int) : PacketData {
        override fun toString(): String {
            return value.toString()
        }
    }

    class ListPacketData(val data: MutableList<PacketData>) : PacketData {
        override fun toString(): String {
            return data.joinToString(separator = ",", prefix = "[", postfix = "]")
        }
    }
}


fun main() {
    val distressSignal = DistressSignal(readInput(13))
    println(distressSignal.part1())
    println(distressSignal.part2())
}
