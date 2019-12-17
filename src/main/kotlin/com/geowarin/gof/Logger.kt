package com.geowarin.gof

typealias Logger = (String) -> Unit

fun Logger.filter(predicate: (String) -> Boolean): Logger = { s ->
  if (predicate(s)) {
    this.invoke(s)
  }
}

fun main() {
  val logger: Logger = System.out::println
  logger("hello")

  val filterLogger = logger.filter { s -> s.startsWith("hell") }
  filterLogger("hello")
  filterLogger("ok")
}
