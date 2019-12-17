package com.geowarin.gof

import arrow.core.Predicate
import arrow.core.andThen
import arrow.core.compose
import java.lang.Exception

class ValidationException(message: String? = null): Exception(message)

class Validator<T> private constructor(private val t: T) {
  private val throwables = mutableListOf<Throwable>()

  fun get(): T {
    if (throwables.isEmpty()) {
      return t
    }
    val e = ValidationException()
    throwables.forEach { e.addSuppressed(it) }
    throw e
  }

  fun validate(message: String, validation: Predicate<T>): Validator<T> {
    try {
      if (!validation(t)) {
        throwables.add(ValidationException(message))
      }
    } catch (e: RuntimeException) {
      throwables.add(e)
    }
    return this
  }

  fun <U> validate(message: String, projection: (T) -> U, validation: Predicate<U>): Validator<T>
      = validate(message, projection.andThen(validation))

  companion object {
    fun <T> of(t: T): Validator<T> = Validator(t)
  }
}

data class User(
  val name: String, val age: Int
)

fun main() {
  Validator.of(User(name = "", age = 42))
    .validate("Name should not be empty", User::name) { it.isNotEmpty() }
//    .validate("Name should not be empty 2", User::name.andThen { it.isNotEmpty() })
//    .validate("Name should not be empty 2", String::isNotEmpty.compose(User::name))
    .validate("Age is not good", User::age) { it < 42 }
    .get()
}
