package com.geowarin.gof

sealed class Vehicle
class Car(val color: String) : Vehicle()
class Moto(val cc: Int) : Vehicle()

// Uncomment this for a compiler error:
// class Boat() : Vehicle()

// The compiler forces the when clause to be exhaustive when used as an expression.
// We have to either provide a "is Boat ->" branch or a "else ->" branch.

fun main() {
    val text = visitor(Moto(250))
    println(text)
}

fun visitor(vehicle: Vehicle): String {
    return when (vehicle) {
        is Car -> "${vehicle.color} car"
        is Moto -> "${vehicle.cc}cc moto"
    }
}
