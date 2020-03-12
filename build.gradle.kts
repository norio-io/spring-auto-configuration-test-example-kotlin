plugins {
  kotlin("jvm") version "1.3.70" apply false
  kotlin("plugin.spring") version "1.3.70" apply false
  id("org.springframework.boot") version "2.2.5.RELEASE" apply false
  id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
}

allprojects {
  group = "io.norio.examples.foo"
  version = "0.0.1-SNAPSHOT"

}

subprojects {
  repositories {
    mavenCentral()
  }

  apply(plugin = "kotlin")
}