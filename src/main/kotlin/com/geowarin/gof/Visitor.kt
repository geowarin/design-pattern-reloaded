package com.geowarin.gof

sealed class Vehicule
class Car(val color: String) : Vehicule()
class Moto(val cc: Int) : Vehicule()

// Uncomment this for a compiler error:
// class Boat() : Vehicule()

// The compiler forces the when clause to be exhaustive when used as an expression.
// We have to either provide a "is Boat ->" branch or a "else ->" branch.

fun main() {
    val vehicule: Vehicule = Moto(250)
    val text = when(vehicule) {
        is Car -> "${vehicule.color} car"
        is Moto -> "${vehicule.cc}cc moto"
    }
    println(text)
}
