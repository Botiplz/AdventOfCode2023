package org.botiplz.days.day12

import org.botiplz.days.AbstractDay

class Day12 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        println(
            lines.sumOf { line ->
                val groups = line.split(' ').last().split(',').map(String::toInt)
                calc(line, groups)
            }
        )
    }

    override fun part2(lines: List<String>) {
        println(
            lines.sumOf { line ->
                val realLine = line.split(' ').first()
                val pushedLine = "$realLine?$realLine?$realLine?$realLine?$realLine"
                val groups = line.split(' ').last().split(',').map(String::toInt)
                val pushedGroups = arrayListOf<Int>()
                pushedGroups.addAll(groups)
                pushedGroups.addAll(groups)
                pushedGroups.addAll(groups)
                pushedGroups.addAll(groups)
                pushedGroups.addAll(groups)
                calc(pushedLine, pushedGroups)
            }
        )
    }

    private fun calc(config: String, groups: List<Int>): Long {
        val cache = hashMapOf<Pair<String, List<Int>>, Long>()
        return count(config, groups, cache)
    }

    private fun count(config: String, groups: List<Int>, cache: HashMap<Pair<String, List<Int>>, Long>): Long {
        if (groups.isEmpty()) return if ("#" in config) 0 else 1
        if (config.isEmpty()) return 0
        return cache.getOrPut(config to groups) {
            var result = 0L
            if (config.first() in ".?")
                result += count(config.drop(1), groups, cache)
            if (config.first() in "#?" && groups.first() <= config.length && "." !in config.take(groups.first()) && (groups.first() == config.length || config[groups.first()] != '#'))
                result += count(config.drop(groups.first() + 1), groups.drop(1), cache)
            result
        }
    }

}