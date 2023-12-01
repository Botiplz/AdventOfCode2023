package org.botiplz.days

import org.botiplz.util.file.loadLines
import java.lang.System.nanoTime
import java.net.URI
import java.nio.file.Path

abstract class AbstractDay {

    fun run() {
        val preIO = nanoTime();
        val testLines = loadLines(Path.of(this::class.java.getResource(testFileName())?.toURI() ?: URI(testFileName())))
        val part1Lines =
            loadLines(Path.of(this::class.java.getResource(part1FileName())?.toURI() ?: URI(part1FileName())))
        val part2Lines =
            loadLines(Path.of(this::class.java.getResource(part2FileName())?.toURI() ?: URI(part2FileName())))
        println("");
        val startnanos = nanoTime();
        test(testLines);
        val testnanos = nanoTime();
        part1(part1Lines);
        val part1nanos = nanoTime();
        part2(part2Lines);
        val part2nanos = nanoTime();
        println("");
        println("initial io: " + ((startnanos - preIO) / 1000000.0) + "ms")
        println("test: " + ((testnanos - startnanos) / 1000000.0) + "ms")
        println("part1: " + ((part1nanos - testnanos) / 1000000.0) + "ms")
        println("part2: " + ((part2nanos - part1nanos) / 1000000.0) + "ms")
        println("");
    }

    protected fun testFileName(): String {
        return "test.txt";
    }

    protected fun part1FileName(): String {
        return "input.txt";
    }

    protected fun part2FileName(): String {
        return "input.txt";
    }

    abstract fun test(lines: List<String>)

    abstract fun part1(lines: List<String>)

    abstract fun part2(lines: List<String>)


}