package com.geowarin.gof

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class ChainOfResponsibilityKtTest {
  @Test
  fun `parse values`() {
    assertEquals(
      "3.0",
      parseString("3").toString()
    )

    assertEquals(
      "x",
      parseString("x").toString()
    )

    assertEquals(
      parseString("+ 3 2").toString(),
      "3.0 + 2.0"
    )

    assertFailsWith<NoSuchElementException> {
      parseString("+ 3")
    }

    assertEquals(
      parseString("+ 42 * - 6.7 var b").toString(),
      "42.0 + 6.7 - var * b"
    )
  }
}
