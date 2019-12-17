package com.geowarin.gof

import java.util.*

val operatorMap: Map<String, Operator> = Operator.values().associateBy { it.opName }

fun parseOperator(token: String) = Optional.ofNullable(operatorMap[token])

fun parseValue(token: String): Optional<Expr> {
  return try {
    Optional.of(Value(token.toDouble()))
  } catch (e: NumberFormatException) {
    Optional.empty()
  }
}

fun parseVariable(token: String): Optional<Expr> = Optional.of(Variable(token))

fun parseBinaryOp(token: String, supplier: () -> Expr): Optional<Expr> =
  parseOperator(token)
    .map { op -> BinaryOp(op, supplier(), supplier()) }

fun parse(
  it: Iterator<String>,
  factory: (String) -> Optional<Expr>
): Expr {
  val token = it.next()
  return factory(token).orElseThrow { IllegalStateException("illegal token $token") }
}

fun parseString(str: String) = parse(str.split(" ").iterator())

enum class Operator(val opName: String) {
  ADD("+"), SUB("-"), MUL("*");
}

sealed class Expr
class Value(val value: Double) : Expr() {
  override fun toString() = value.toString()
}

class Variable(val name: String) : Expr() {
  override fun toString() = name
}

class BinaryOp(val operator: Operator, val left: Expr, val right: Expr) : Expr() {
  override fun toString() = "$left ${operator.opName} $right"
}

fun main() {
  val expr: Expr = parseString("+ 2 * a 3")
  println(expr)
}

fun parse(it: Iterator<String>): Expr {
  return parse(it) { token: String ->
    parseBinaryOp(token) { parse(it) }
      .or { parseValue(token).or { parseVariable(token) } }
  }
}
