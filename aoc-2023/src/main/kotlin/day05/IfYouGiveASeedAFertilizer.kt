package day05

import utils.readInput

class IfYouGiveASeedAFertilizer(private val input: String) {

    fun part1(): Long {
        val seedsAndMaps = parseInput()
        return seedsAndMaps.seeds.minOf { seed ->
            seedsAndMaps.maps.findLocationForSeed(seed)
        }
    }

    fun part2(): Long {
        val seedRangeAndMaps = parseInputWithSeedRange()
        return seedRangeAndMaps.seedRanges.flatMap { seedRange ->
            seedRange.map { seed ->
                seedRangeAndMaps.maps.findLocationForSeed(seed)
            }
        }.min()
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
                LongRange(start.toLong(), start.toLong() + length.toLong())
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
    private val seedToLocationMap = mutableMapOf<Long, Long>()

    fun findLocationForSeed(seed: Long): Long {
        if (seedToLocationMap.containsKey(seed)) {
            return seedToLocationMap.getValue(seed)
        }
        val soil = findInRangeOrDefault(seed, seedToSoilMap)
        val fertilizer = findInRangeOrDefault(soil, soilToFertilizerMap)
        val water = findInRangeOrDefault(fertilizer, fertilizerToWaterMap)
        val light = findInRangeOrDefault(water, waterToLightMap)
        val temperature = findInRangeOrDefault(light, lightToTemperatureMap)
        val humidity = findInRangeOrDefault(temperature, temperatureToHumidityMap)
        val location = findInRangeOrDefault(humidity, humidityToLocationMap)
        seedToLocationMap[seed] = location
        return location
    }

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
    val ifyourgiveaseedafertilizer = IfYouGiveASeedAFertilizer(readInput(5))
    println(ifyourgiveaseedafertilizer.part1())
    println(ifyourgiveaseedafertilizer.part2())
}
