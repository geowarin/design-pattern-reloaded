package com.geowarin.gof

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence

typealias Observer = (Double) -> Unit

fun parseCSV(path: Path, observer: Observer) {
  csvDoubleSequence(path)
    .forEach { value -> observer(value) }
}

fun csvDoubleSequence(path: Path): Sequence<Double> {
  return path.linesSequence
    .flatMap { it.splitToSequence(",") }
    .map { it.toDouble() }
}

val Path.linesSequence get() = Files.lines(this).asSequence()

fun getResource(path: String): Path = Paths.get(object {}.javaClass.getResource(path).toURI())

fun main() {
  val path = getResource("/test.csv")

  var sum = 0.0
  parseCSV(path) { value -> sum += value }
  println(sum)

  // generating a sequence is even more straightforward
  val sum2 = csvDoubleSequence(path).sum()
  println(sum2)
}
