package io.norio.examples.foo.autoconfigure

import io.norio.examples.foo.Foo
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnMissingBean(Foo::class)
@ConditionalOnProperty(prefix = "io.norio.foo", name = ["auto-configure"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(FooProperties::class)
class FooAutoConfiguration(private val properties: FooProperties) {

  @Bean
  fun foo() = Foo(
      stringProp = properties.stringProp,
      stringsProp = properties.stringsProp,
      intProp = properties.intProp,
      booleanProp = properties.booleanProp
  )
}