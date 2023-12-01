package org.botiplz.util.file

import java.nio.file.Files
import java.nio.file.Path

fun loadLines(path: Path): List<String> {
    return Files.readAllLines(path);
}

