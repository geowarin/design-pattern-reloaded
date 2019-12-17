package com.geowarin.gof

val operatorMap: Map<String, Operator> = Operator.values().associateBy { it.opName }

fun parseOperator(token: String): Operator? = operatorMap[token]

fun parseValue(token: String): Expr? = token.toDoubleOrNull()?.let { Value(it) }

fun parseVariable(token: String): Expr = Variable(token)

fun parseBinaryOp(token: String, supplier: () -> Expr): Expr? =
  parseOperator(token)?.let { BinaryOp(it, supplier(), supplier()) }

fun parseUsing(iterator: Iterator<String>, factory: (String) -> Expr): Expr = factory(iterator.next())

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

fun parse(iterator: Iterator<String>): Expr {
  return parseUsing(iterator) { token: String ->
    parseBinaryOp(token) { parse(iterator) }
      ?: parseValue(token)
      ?: parseVariable(token)
  }
}
