package org.botiplz.days.day19

import org.botiplz.days.AbstractDay
import org.botiplz.util.list.split
import org.botiplz.util.stream.multiply
import kotlin.math.max
import kotlin.math.min

class Day19 : AbstractDay() {
    override fun test(lines: List<String>) {
        part1(lines)
    }

    override fun part1(lines: List<String>) {
        val split = lines.split("")
        val rulesLines = split.first()
        val rangeLines = split.last()
        println(calcAcceptedParts(parseRules(rulesLines), parseRanges(rangeLines)).sumOf { it.parts })
    }

    override fun part2(lines: List<String>) {
        val split = lines.split("")
        val rulesLines = split.first()
        val rules = parseRules(rulesLines)

        println(
            calcAcceptedParts(
                rules, listOf(
                    PartRange(
                        mapOf('x' to 1L..4000L, 'm' to 1L..4000L, 'a' to 1L..4000L, 's' to 1L..4000L),
                        "in"
                    )
                )
            ).sumOf { it.amount }
        )

    }

    private fun calcAcceptedParts(rules: Map<String, Rule>, ranges: List<PartRange>): List<PartRange> {
        val rangesInGoal = arrayListOf<PartRange>()
        val rangesToCheck = ArrayList(ranges)

        while (rangesToCheck.isNotEmpty()) {
            val first = rangesToCheck.first()
            rangesToCheck.removeAt(0)

            val newSplit = rules[first.label]!!.split(first)
            rangesToCheck.addAll(newSplit.filter { rules.containsKey(it.label) })
            rangesInGoal.addAll(newSplit.filter { !rules.containsKey(it.label) })

        }
        return rangesInGoal.filter { it.label == "A" }
    }

    private fun parseRanges(lines: List<String>): List<PartRange> {
        return lines.map { line ->
            val valueMap = hashMapOf<Char, LongRange>()
            for (fieldString in line.drop(1).dropLast(1).split(',')) {
                val key = fieldString.split('=').first().first()
                val value = fieldString.split('=').last().toLong()
                valueMap[key] = value..value
            }
            PartRange(valueMap, "in")
        }
    }

    class PartRange(private val ranges: Map<Char, LongRange>, val label: String) {
        val parts = ranges.values.sumOf { it.sum() }
        val amount = ranges.values.map { it.last - it.first + 1 }.multiply()

        fun inRange(value: Int, field: Char): Boolean {
            return ranges[field]!!.contains(value)
        }

        fun splittable(valueExclusive: Int, field: Char): Boolean {
            return ranges[field]!!.first < valueExclusive && ranges[field]!!.last >= valueExclusive
        }

        fun after(value: Int, field: Char): Boolean {
            return ranges[field]!!.first > value
        }

        fun before(value: Int, field: Char): Boolean {
            return ranges[field]!!.last > value
        }


        fun split(
            value: Long,
            field: Char,
            symbolToCheck: Char,
            successLabel: String,
            failLabel: String
        ): List<PartRange> {

            val splitRanges = arrayListOf<PartRange>()
            val fieldRange = ranges[field]!!
            if (fieldRange.contains(value)) {
                val copy = HashMap(ranges)
                copy[field] = value..value
                splitRanges.add(PartRange(copy, if (symbolToCheck == '=') successLabel else failLabel))
            }

            if (fieldRange.last > value) {
                val copy = HashMap(ranges)
                copy[field] = max(fieldRange.first, value + 1)..fieldRange.last
                splitRanges.add(PartRange(copy, if (symbolToCheck == '>') successLabel else failLabel))
            }

            if (fieldRange.first < value) {
                val copy = HashMap(ranges)
                copy[field] = fieldRange.first..min(fieldRange.last, value - 1)
                splitRanges.add(PartRange(copy, if (symbolToCheck == '<') successLabel else failLabel))
            }

            return splitRanges
        }

        override fun toString(): String {
            return "{$ranges, $parts}"
        }
    }

    private fun parseRules(lines: List<String>): Map<String, Rule> {
        return lines.map { Rule(it) }.groupBy { it.label }.mapValues { it.value.first() }
    }

    class Rule(code: String) {
        private val conditions: List<Condition>
        val label: String = code.split('{').first()

        init {
            val conditionsString = code.split('{').last().dropLast(1)
            conditions = conditionsString.split(',').map { Condition(label, it) }
        }

        fun split(range: PartRange): List<PartRange> {
            var invalidRanges = arrayListOf(range)
            val validRanges = arrayListOf<PartRange>()
            for (condition in conditions) {
                val newInvalidRanges = arrayListOf<PartRange>()
                for (partRange in invalidRanges) {
                    val splitRanges = condition.split(partRange)
                    validRanges.addAll(splitRanges.filter { it.label != label })
                    newInvalidRanges.addAll(splitRanges.filter { it.label == label })
                }
                invalidRanges = newInvalidRanges
            }
            return validRanges
        }

    }

    class Condition(private val currentLabel: String, code: String) {
        private val field: Char
        private val symbol: Char
        val value: Long
        private val targetLabel: String

        init {
            val conditionSplit = code.split(':')
            targetLabel = conditionSplit.last()
            if (conditionSplit.size > 1) {
                val conditionString = conditionSplit.first()
                field = conditionString.first()
                symbol = conditionString[1]
                value = conditionString.drop(2).toLong()
            } else {
                field = 'a'
                symbol = '>'
                value = -1
            }
        }

        override fun toString(): String {
            return "$field$symbol$value:$targetLabel"
        }

        fun split(range: PartRange): List<PartRange> {
            return range.split(value, field, symbol, targetLabel, currentLabel)
        }

    }


    class RangeSplit(val label: String, val success: List<PartRange>, val other: List<PartRange>)

}