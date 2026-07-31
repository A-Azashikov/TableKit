plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
    implementation("org.apache.poi:poi:5.5.1")
    implementation("org.apache.poi:poi-ooxml:5.5.1")
}

application {
    mainClass = "io.github.a_azashikov.tablekit.example.simple.SimpleExample"
}

tasks.register<JavaExec>("runGroupExample") {
    mainClass = "io.github.a_azashikov.tablekit.example.group.GroupExample"
    classpath = sourceSets.main.get().runtimeClasspath
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
