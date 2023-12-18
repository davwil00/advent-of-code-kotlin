package day05

import utils.readInput

class IfYouGiveASeedAFertilizer(private val input: String) {

    fun part1(): Long {
        val seedsAndMaps = parseInput()
        return seedsAndMaps.seeds.minOf { seed ->
            seedsAndMaps.maps.findLocationForSeed(seed)
        }
    }

    // Something like calculating the range overlaps e.g.
    // soil 98-99 -> seed 50 - 51
    // soil 50 - 98 -> seed 52 - 99

    // fertilizer 0 - 36 -> soil 15 - 51
    // fertilizer 52 - 53 -> soil 37 -> 38
    // fertilizer 0 - 14 -> 39 -> 53

    // Therefore seed 79 14 = 79 - 92
    // all fit in seed band for same soil - basically pass round lists of ranges rather than individual numbers
    fun part2(): Long {
//        val seedRangeAndMaps = parseInputWithSeedRange()
//        return seedRangeAndMaps.seedRanges.flatMap { seedRange ->
//
//        }.min()
        return 1
    }

    private fun parseInput(): SeedsAndMaps {
        val components = input.split("\n\n")
        val seeds = components[0].substring(7).split(" ").map { it.toLong() }
        return SeedsAndMaps(seeds, createMapContainer(components.drop(1)))
    }

    private fun parseInputWithSeedRange(): SeedRangeAndMaps {
        val components = input.split("\n\n")
        val seeds = components[0]
            .substring(7)
            .split(" ")
            .chunked(2)
            .map { (start, length) ->
                LongRange(start.toLong(), start.toLong() + length.toLong() - 1)
            }
        return SeedRangeAndMaps(seeds, createMapContainer(components.drop(1)))
    }

    private fun createMapContainer(components: List<String>) = MapContainer(
        createMap(components[0]),
        createMap(components[1]),
        createMap(components[2]),
        createMap(components[3]),
        createMap(components[4]),
        createMap(components[5]),
        createMap(components[6]),
    )
}

fun createMap(mapDef: String): Map<LongRange, LongRange> = buildMap {
    mapDef.lines()
        .drop(1)
        .forEach { mapping ->
            val (seedStart, soilStart, rangeLength) = mapping.split(" ").map { it.toLong() }
            put(LongRange(soilStart, soilStart + rangeLength), LongRange(seedStart, seedStart + rangeLength))
        }
}

data class SeedsAndMaps(val seeds: List<Long>, val maps: MapContainer)
data class SeedRangeAndMaps(val seedRanges: List<LongRange>, val maps: MapContainer)

data class MapContainer(
    val seedToSoilMap: Map<LongRange, LongRange>,
    val soilToFertilizerMap: Map<LongRange, LongRange>,
    val fertilizerToWaterMap: Map<LongRange, LongRange>,
    val waterToLightMap: Map<LongRange, LongRange>,
    val lightToTemperatureMap: Map<LongRange, LongRange>,
    val temperatureToHumidityMap: Map<LongRange, LongRange>,
    val humidityToLocationMap: Map<LongRange, LongRange>
) {
    fun findLocationForSeed(seed: Long): Long {
        val soil = findInRangeOrDefault(seed, seedToSoilMap)
        val fertilizer = findInRangeOrDefault(soil, soilToFertilizerMap)
        val water = findInRangeOrDefault(fertilizer, fertilizerToWaterMap)
        val light = findInRangeOrDefault(water, waterToLightMap)
        val temperature = findInRangeOrDefault(light, lightToTemperatureMap)
        val humidity = findInRangeOrDefault(temperature, temperatureToHumidityMap)
        return findInRangeOrDefault(humidity, humidityToLocationMap)
    }

//    fun findLocationForSeedRange(seedRange: LongRange) {
//        val soilRanges = mutableListOf<LongRange>()
//        var startingSeedRange = seedRange.first
//
//        do {
//            val maxSeedNumberProcessed = seedToSoilMap.keys.first { startingSeedRange in it }.last
//            if (maxSeedNumberProcessed < seedRange.last) {
//                startingSeedRange = maxSeedNumberProcessed + 1
//            }
//            soilRanges.add(LongRange(startingSeedRange, seedRange.last))
//        } while(startingSeedRange < seedRange.last)
//    }

    fun findMinLocationForSeedRange(seedRange: LongRange): Long {
        val soilRanges = findNextRangesForRange(seedRange, seedToSoilMap)
        val fertilizerRanges = soilRanges.flatMap { findNextRangesForRange(it, soilToFertilizerMap) }
        val waterRanges = fertilizerRanges.flatMap { findNextRangesForRange(it, fertilizerToWaterMap) }
        val lightRanges = waterRanges.flatMap { findNextRangesForRange(it, waterToLightMap) }
        val temperatureRanges = lightRanges.flatMap { findNextRangesForRange(it, lightToTemperatureMap) }
        val humidityRanges = temperatureRanges.flatMap { findNextRangesForRange(seedRange, temperatureToHumidityMap) }
        val locationRanges = humidityRanges.flatMap { findNextRangesForRange(seedRange, humidityToLocationMap) }
        return locationRanges.minOf { it.first }
    }

    fun findNextRangesForRange(range: LongRange, map: Map<LongRange, LongRange>): List<LongRange> {
        val ranges = mutableListOf<LongRange>()
        var startingRange = range.first

        do {
            val maxNumberProcessed = map.keys.first { startingRange in it }.last
            if (maxNumberProcessed < range.last) {
                startingRange = maxNumberProcessed + 1
            }
            ranges.add(LongRange(startingRange, range.last))
        } while(startingRange < range.last)

        return ranges
    }

//    fun findRanges

    private fun findInRangeOrDefault(target: Long, rangeMap: Map<LongRange, LongRange>): Long {
        val match = rangeMap.entries.find { (key, _) ->
            target in key
        }

        return if (match != null) {
            val offset = target - match.key.first
            match.value.first + offset
        } else {
            target
        }
    }
}


fun main() {
    val ifYouGiveASeedAFertilizer = IfYouGiveASeedAFertilizer(readInput(5))
    println(ifYouGiveASeedAFertilizer.part1())
    println(ifYouGiveASeedAFertilizer.part2())
}
