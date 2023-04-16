@file:Suppress("DSL_SCOPE_VIOLATION")

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.dependency.updates)
}

allprojects {
    apply {
        plugin("org.jmailen.kotlinter")
    }

    group = "io.github.lukwol"
    version = "1.3.1"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"

            if (project.findProperty("enableComposeCompilerReports") == "true") {
                val destinationDir = project.buildDir.absolutePath + "/compose_metrics"
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$destinationDir",
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$destinationDir"
                )
            }
        }
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        val version = candidate.version.toLowerCaseAsciiOnly()

        listOf("-alpha", "-beta", "-rc")
            .any { it in version }
    }
}
