package org.botiplz.days.day02

import org.botiplz.days.AbstractDay
import org.botiplz.util.stream.multiply

class Day02 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        val totalBag = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val games = lines.map { parseGame(it) }
        val validgames = games.filter { it.isPossible(totalBag) }
        println(validgames.map { it.id }.sum())
    }

    override fun part2(lines: List<String>) {
        val games = lines.map { parseGame(it) }
        println(games.map { it.getPower() }.sum())
    }


    fun parseGame(line: String): Game {
        val id = line.split(':').first().split(' ').last().toInt()
        val bags = line.split(':').last().split(';').map {
            val bag: HashMap<String, Int> = hashMapOf();
            it.split(',').forEach {
                val split = it.trim().split(' ')
                bag[split.last().trim()] = split.first().trim().toInt()
            }
            bag
        }
        return Game(id, bags)
    }

    data class Game(val id: Int, val bags: List<HashMap<String, Int>>) {
        fun isPossible(bag: Map<String, Int>): Boolean {
            return bags.none { it.keys.any { key -> it[key]!! > bag[key]!! } }
        }

        fun getPower(): Int {
            val minBag: HashMap<String, Int> = hashMapOf();
            bags.forEach { bag ->
                bag.keys.forEach { key ->
                    minBag.putIfAbsent(key, bag[key]!!)
                    if (bag[key]!! > minBag[key]!!) {
                        minBag[key] = bag[key]!!
                    }
                }
            }
            return minBag.values.multiply()
        }
    }
}
