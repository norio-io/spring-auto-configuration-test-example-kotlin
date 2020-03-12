package io.norio.examples.foo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FooTest {

  @Test
  fun boringTest() {

    val foo = Foo(stringProp = "bar", stringsProp = listOf("hoge", "fuga"), intProp = 1234, booleanProp = false)

    assertEquals("bar", foo.returnStringProp())
    assertEquals(listOf("hoge", "fuga"), foo.returnStringsProp())
    assertEquals("bar", foo.returnStringProp())
    assertEquals(1234, foo.returnIntProp())
    assertEquals(false, foo.returnBooleanProp())
  }
}