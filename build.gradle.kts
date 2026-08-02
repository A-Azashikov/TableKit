plugins {
    base
}

// Installs/updates the pre-push git hook into .git/hooks/pre-push.
val installGitHooks = tasks.register<Copy>("installGitHooks") {
    group = "build"
    description = "Installs the pre-push git hook from hooks/pre-push into .git/hooks/."

    val gitDir = providers.exec {
        commandLine("git", "rev-parse", "--git-dir")
    }.standardOutput.asText.map { it.trim() }

    val hooksDir = file(gitDir).resolve("hooks")

    from(layout.projectDirectory.dir("hooks")) {
        include("pre-push")
    }
    into(hooksDir)

    doLast {
        val hookFile = hooksDir.resolve("pre-push")
        hookFile.setExecutable(true, false)
        logger.lifecycle("Git hook installed: {}", hookFile)
    }
}

tasks.named("build") {
    dependsOn(installGitHooks)
}

// Automatically install/update the git hook on every build of any (sub)project.
gradle.projectsEvaluated {
    allprojects {
        tasks.matching { it.name == "build" }.configureEach {
            dependsOn(rootProject.tasks.named("installGitHooks"))
        }
    }
}