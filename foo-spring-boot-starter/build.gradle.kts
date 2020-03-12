dependencies {
  api(project(":foo"))
  api(project(":foo-spring-boot-autoconfigure"))
}

tasks.getByName<Jar>("jar") {
  enabled = true
}