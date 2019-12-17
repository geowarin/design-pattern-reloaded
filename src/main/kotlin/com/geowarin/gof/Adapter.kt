package com.geowarin.gof

import arrow.syntax.function.partially1

enum class Level {
  WARNING, ERROR
}

typealias Logger2 = (Level, String) -> Unit

fun Logger2.level(level: Level): Logger = { s -> this.invoke(level, s) }

fun main() {
  val logger2: Logger2 = { level, msg -> println("$level $msg") }
  logger2(Level.ERROR, "abort abort !")

  // with extension function
  val logger1 = logger2.level(Level.WARNING)
  logger1("Oh oh")

  // partial application with arrow
  val oldLogger: Logger = logger2.partially1(Level.WARNING)
  oldLogger("oh noes!")
}
