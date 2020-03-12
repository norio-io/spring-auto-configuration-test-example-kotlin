package io.norio.examples.foo

class Foo(private val stringProp: String, private val stringsProp: List<String>, private val intProp: Int, private val booleanProp: Boolean) {

  fun returnStringProp() = stringProp
  fun returnStringsProp() = stringsProp
  fun returnIntProp() = intProp
  fun returnBooleanProp() = booleanProp
}