package io.norio.examples.foo.autoconfigure

import io.norio.examples.foo.Foo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class FooAutoConfigurationTest {

  private lateinit var context: AnnotationConfigApplicationContext

  @BeforeEach
  fun setUp() {
    context = AnnotationConfigApplicationContext()
  }

  @AfterEach
  fun tearDown() {
    context.close()
  }

  @Test
  fun testWithoutAnyProps() {
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("default", foo.returnStringProp())
    assertEquals(emptyList<String>(), foo.returnStringsProp())
    assertEquals(-1, foo.returnIntProp())
    assertFalse(foo.returnBooleanProp())
  }

  @Test
  fun testWithStringProp() {
    TestPropertyValues.of(
        "io.norio.foo.auto-configure:true",
        "io.norio.foo.string-prop:foo"
    ).applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("foo", foo.returnStringProp())
    assertEquals(emptyList<String>(), foo.returnStringsProp())
    assertEquals(-1, foo.returnIntProp())
    assertFalse(foo.returnBooleanProp())
  }

  @Test
  fun testWithStringsProp() {
    TestPropertyValues.of(
        "io.norio.foo.auto-configure:true",
        "io.norio.foo.strings-prop:hoge,fuga"
    ).applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("default", foo.returnStringProp())
    assertEquals(listOf("hoge", "fuga"), foo.returnStringsProp())
    assertEquals(-1, foo.returnIntProp())
    assertFalse(foo.returnBooleanProp())
  }

  @Test
  fun testWithIntProp() {
    TestPropertyValues.of(
        "io.norio.foo.auto-configure:true",
        "io.norio.foo.int-prop:987"
    ).applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("default", foo.returnStringProp())
    assertEquals(emptyList<String>(), foo.returnStringsProp())
    assertEquals(987, foo.returnIntProp())
    assertFalse(foo.returnBooleanProp())
  }

  @Test
  fun testWithBooleanProp() {
    TestPropertyValues.of(
        "io.norio.foo.auto-configure:true",
        "io.norio.foo.boolean-prop:true"
    ).applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("default", foo.returnStringProp())
    assertEquals(emptyList<String>(), foo.returnStringsProp())
    assertEquals(-1, foo.returnIntProp())
    assertTrue(foo.returnBooleanProp())
  }

  @Test
  fun testWithAllProps() {
    TestPropertyValues.of(
        "io.norio.foo.auto-configure:true",
        "io.norio.foo.string-prop:foo",
        "io.norio.foo.strings-prop:hoge,fuga",
        "io.norio.foo.int-prop:123",
        "io.norio.foo.boolean-prop:true"
    ).applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("foo", foo.returnStringProp())
    assertEquals(listOf("hoge", "fuga"), foo.returnStringsProp())
    assertEquals(123, foo.returnIntProp())
    assertTrue(foo.returnBooleanProp())
  }

  @Test
  fun testWithExistingBean() {
    TestPropertyValues.of(
        "io.norio.foo.auto-configure:true",
        "io.norio.foo.string-prop:foo",
        "io.norio.foo.strings-prop:hoge,fuga",
        "io.norio.foo.int-prop:123",
        "io.norio.foo.boolean-prop:true"
    ).applyTo(context)
    context.registerBean(Foo::class.java, "existingFoo", listOf("already", "exists"), 999, false)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("existingFoo", foo.returnStringProp())
    assertEquals(listOf("already", "exists"), foo.returnStringsProp())
    assertEquals(999, foo.returnIntProp())
    assertFalse(foo.returnBooleanProp())
  }

  @Test
  fun testAutoConfigureByMatchIfMissing() {
    TestPropertyValues.of(
        "io.norio.foo.string-prop:foo",
        "io.norio.foo.strings-prop:hoge,fuga",
        "io.norio.foo.int-prop:123",
        "io.norio.foo.boolean-prop:true"
    ).applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("foo", foo.returnStringProp())
    assertEquals(listOf("hoge", "fuga"), foo.returnStringsProp())
    assertEquals(123, foo.returnIntProp())
    assertTrue(foo.returnBooleanProp())
  }

  @Test
  fun testDisableAutoConfigure() {
    TestPropertyValues.of("io.norio.foo.auto-configure:false").applyTo(context)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    assertThrows(NoSuchBeanDefinitionException::class.java) { context.getBean(Foo::class.java) }
  }

  @Test
  fun testDisableAutoConfigureWithExistingBean() {
    TestPropertyValues.of("io.norio.foo.auto-configure:false").applyTo(context)
    context.registerBean(Foo::class.java, "existingFoo", listOf("already", "exists"), 999, false)
    context.register(FooAutoConfiguration::class.java)
    context.refresh()

    val foo = context.getBean(Foo::class.java)

    assertEquals("existingFoo", foo.returnStringProp())
    assertEquals(listOf("already", "exists"), foo.returnStringsProp())
    assertEquals(999, foo.returnIntProp())
    assertFalse(foo.returnBooleanProp())
  }
}