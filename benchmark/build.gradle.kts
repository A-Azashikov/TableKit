plugins {
    java
    id("me.champeau.jmh") version "0.7.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
    implementation(libs.poi)
    implementation(libs.poi.ooxml)
    implementation(libs.jmh.core)
    annotationProcessor(libs.jmh.annotation)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// Configure JMH
jmh {
    warmupIterations = 3
    iterations = 5
    fork = 2
    benchmarkMode = listOf("avgt", "thrpt")
    timeUnit = "ms"
    resultFormat = "JSON"
    resultsFile = project.layout.buildDirectory.file("reports/jmh/results.json").get().asFile
    includes = listOf(".*Benchmark")
}