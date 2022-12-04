package day04

import utils.readInputLines

class CampCleanup(input: List<String>) {

    companion object {
        private val INPUT_REGEX = Regex("""(\d+)-(\d+),(\d+)-(\d+)""")
    }

    private val elfPairs = input.map { pair ->
        val match = INPUT_REGEX.matchEntire(pair)
        val groups = match!!.groupValues
        IntRange(groups[1].toInt(), groups[2].toInt()) to IntRange(groups[3].toInt(), groups[4].toInt())
    }

    fun findCompletelyOverlappingPairs() = elfPairs.count { (elf1, elf2) ->
            elf1.contains(elf2.first) && elf1.contains(elf2.last) ||
                    elf2.contains(elf1.first) && elf2.contains(elf1.last)
        }

    fun findOverlappingPairs() = elfPairs.count { (elf1, elf2) ->
        elf1.contains(elf2.first) || elf1.contains(elf2.last) ||
                elf2.contains(elf1.first) || elf2.contains(elf1.last)
    }
}

fun main() {
    val campCleanup = CampCleanup(readInputLines(4))
    println(campCleanup.findCompletelyOverlappingPairs())
    println(campCleanup.findOverlappingPairs())
}
