package org.botiplz.days.day07

import org.botiplz.days.AbstractDay

class Day07 : AbstractDay() {
    override fun test(lines: List<String>) {
        part2(lines)
    }

    override fun part1(lines: List<String>) {
        play(lines, 1)
    }

    override fun part2(lines: List<String>) {
        play(lines, 2)
    }

    private fun play(lines: List<String>, part: Int) {
        val bids = lines.map { line -> Bid(line, part) }
        println(bids.sorted().mapIndexed { index, bid -> (index + 1) * bid.bid }.sum())
    }

    class Bid(line: String, private val part: Int = 1) : Comparable<Bid> {

        private val hand: String
        val bid: Int
        private val handWithType: String

        init {
            val split = line.split(' ')
            hand = split.first()
            bid = split.last().toInt()

            val cardMap = HashMap(hand.map { card -> card to hand.filter { it == card }.length }.toMap())

            if (part == 2) {
                val jokerCount = cardMap['J'] ?: 0
                cardMap['J'] = 0
                val maxKey = cardMap.keys.maxBy { cardMap[it]!! }
                cardMap[maxKey] = cardMap[maxKey]!! + jokerCount
            }

            val type = calculateMethod(cardMap)
            handWithType = type + hand
        }

        private fun calculateMethod(cardMap: HashMap<Char, Int>): Char {
            //Five of a kind
            if (cardMap.values.any { it == 5 }) {
                return '9'
            }
            //Four of a kind
            else if (cardMap.values.any { it == 4 }) {
                return '8'
            }
            //Full house
            else if (cardMap.values.any { it == 3 } && cardMap.values.any { it == 2 }) {
                return '7'
            }
            //Three of a kind
            else if (cardMap.values.any { it == 3 }) {
                return '6'
            }
            //Two pairs
            else if (cardMap.values.filter { it == 2 }.size == 2) {
                return '5'
            }
            //Pair
            else if (cardMap.values.any { it == 2 }) {
                return '4'
            }
            //Single
            else {
                return '3'
            }
        }


        override fun toString(): String {
            return "${hand}_$bid"
        }

        override fun compareTo(other: Bid): Int {
            for (index in handWithType.indices) {
                val myCard = handWithType[index]
                val otherCard = other.handWithType[index]

                val order = ORDERS[part]!!
                val myIndex = order.indexOf(myCard)
                val otherIndex = order.indexOf(otherCard)
                if (myIndex != otherIndex) {
                    return otherIndex - myIndex
                }
            }
            return 0
        }

        companion object {
            val ORDERS = mapOf(
                1 to "AKQJT98765432",
                2 to "AKQT98765432J"
            )
        }
    }


}