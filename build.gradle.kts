plugins {
    java
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.asciidoctor) apply false
}

allprojects {
    group = "com.coralstay"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}
