package com.geowarin.gof

import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class ChainOfResponsibilityKtTest {
  @Test
  fun `parse value`() {
    assertEquals(
      "3.0",
      parseString("3").toString()
    )
  }

  @Test
  fun `parse variable`() {
    assertEquals(
      "x",
      parseString("x").toString()
    )
  }

  @Test
  fun `parse operator`() {
    assertEquals(
      parseString("+ 3 2").toString(),
      "3.0 + 2.0"
    )

    assertFailsWith<NoSuchElementException> {
      parseString("+ 3")
    }
  }

  @Test
  fun `ignores dandling symbols`() {
    assertEquals(
      parseString("+ 3 2 whatever").toString(),
      "3.0 + 2.0"
    )
  }

  @Test
  fun `parse complex`() {
    assertEquals(
      parseString("+ 42 * - 6.7 var b").toString(),
      "42.0 + 6.7 - var * b"
    )
  }
}
