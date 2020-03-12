package io.norio.examples.foo.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "io.norio.foo")
@ConstructorBinding
data class FooProperties(
    val stringProp: String = "default",
    val stringsProp: List<String> = listOf(),
    val intProp: Int = -1,
    val booleanProp: Boolean = false
) {
}