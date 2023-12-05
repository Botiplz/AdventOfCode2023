package org.botiplz.days.day05

import org.botiplz.days.AbstractDay
import org.botiplz.util.range.intersectRange
import org.botiplz.util.range.intersectsRange
import org.botiplz.util.range.splitByOther

class Day05 : AbstractDay() {
    override fun test(lines: List<String>) {

        part2(lines)
    }

    override fun part1(lines: List<String>) {
        val seeds = lines.first().split(':').last().trim().split(' ').map(String::toLong)

        //parse
        val allMaps = parseMappings(lines)

        println(seeds.map { mapSingleSeed(it, allMaps) }.min())
    }

    override fun part2(lines: List<String>) {

        val seedRanges = lines.first().split(':').last().trim().split(' ').map(String::toLong)
        val seeds = arrayListOf<LongRange>()
        for (index in seedRanges.indices step 2) {
            seeds.add(seedRanges[index]..<seedRanges[index] + seedRanges[index + 1])
        }

        val allMaps = parseMappings(lines)
        println(seeds.map { mapSeedrange(it, allMaps).map { it.first }.min() }.min())
    }

    fun mapSingleSeed(seed: Long, maps: List<Mapping>): Long {
        var endSeed = seed;
        for (map in maps) {
            val seedbefore = endSeed;
            val seedAfter = map.mapValue(seedbefore)
            endSeed = seedAfter
        }
        return endSeed;
    }

    fun mapSeedrange(seedRange: LongRange, maps: List<Mapping>): List<LongRange> {
        var endSeed = listOf(seedRange);
        for (map in maps) {
            val seedbefore = endSeed;
            val seedAfter = map.mapRanges(seedbefore)
            endSeed = seedAfter
        }
        return endSeed;
    }

    fun parseMappings(lines: List<String>): ArrayList<Mapping> {
        val allMaps = arrayListOf<Mapping>()
        var currentMap = Mapping()
        for (line in lines) {

            if (line.endsWith(":") && currentMap.mappings.isNotEmpty()) {
                currentMap.mappings.sortedBy { it.source }
                allMaps.add(currentMap)
                currentMap = Mapping()
            }

            if (line.isNotEmpty() && line.all { it.isDigit() || it.isWhitespace() }) {
                val split = line.split(' ')
                val destination = split.first().toLong()
                val source = split[1].toLong()
                val range = split.last().toLong()
                currentMap.mappings.add(MappingLine(destination, source, range))
            }
        }
        if (currentMap.mappings.isNotEmpty()) {
            currentMap.mappings.sortedBy { it.source }
            allMaps.add(currentMap)
        }
        return allMaps
    }

    class Mapping(val mappings: ArrayList<MappingLine> = arrayListOf()) {

        /***
         * Just maps the value
         */
        fun mapValue(value: Long): Long {
            return mappings.filter { it.hasValue(value) }.map { it.mapValue(value) }.firstOrNull() ?: value;
        }

        /***
         * Creates a mapped range of the intersecting range
         */
        fun mapRanges(oldRanges: List<LongRange>): List<LongRange> {
            val newRanges = arrayListOf<LongRange>()
            val uncheckedRanges = ArrayList(oldRanges)

            for (mappingLine in mappings) {
                val toRemove = arrayListOf<LongRange>()
                val toAdd = arrayListOf<LongRange>()

                for (oldRange in uncheckedRanges.filter { mappingLine.sourceRange.intersectsRange(it) }) {
                    //map intersection
                    newRanges.add(mappingLine.mapRangeIntersection(oldRange))
                    toRemove.add(oldRange)

                    //add non intersecting parts for future checks
                    toAdd.addAll(oldRange.splitByOther(mappingLine.sourceRange))
                }
                uncheckedRanges.removeAll(toRemove)
                uncheckedRanges.addAll(toAdd)

            }

            newRanges.addAll(uncheckedRanges)
            return newRanges
        }

    }

    class MappingLine(val destination: Long, val source: Long, val range: Long) {

        val sourceRange = source..<source + range


        fun hasValue(value: Long): Boolean {
            return sourceRange.contains(value);
        }

        /***
         * Just maps the value
         */
        fun mapValue(value: Long): Long {
            return (value - source) + destination
        }

        /***
         * Creates a mapped range of the intersecting range
         */
        fun mapRangeIntersection(value: LongRange): LongRange {
            val intersection = value.intersectRange(sourceRange)
            return (intersection.first - source + destination)..(intersection.last - source + destination)
        }

    }

}