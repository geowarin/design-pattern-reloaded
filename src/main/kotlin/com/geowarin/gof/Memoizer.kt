package com.geowarin.gof

import java.math.BigInteger
import java.util.concurrent.atomic.AtomicReference

fun <V, R> memoize(f: (V, (V) -> R) -> R): (V) -> R {
  val map = mutableMapOf<V, R>()

  val ref = AtomicReference<(V) -> R>()
  ref.set { value -> map.computeIfAbsent(value) { v -> f(v, ref.get()) } }
  return ref.get()
}

val fibo: (Int) -> BigInteger = memoize { n, fib ->
  when (n) {
    0, 1 -> BigInteger.ONE
    else -> fib(n - 1) + fib(n - 2)
  }
}

fun main() {
  IntRange(0, 20)
    .map { fibo(it) }
    .forEach(::println)
}
