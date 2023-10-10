plugins {
    id("lukmadev.kover")
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    dependencies {
        classpath(libs.bundles.classpath)
    }
}

val detektReportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
}

subprojects {
    plugins.withType<io.gitlab.arturbosch.detekt.DetektPlugin> {
        tasks.withType<io.gitlab.arturbosch.detekt.Detekt> detekt@{
            detektReportMerge.configure {
                input.from(this@detekt.sarifReportFile)
            }
            finalizedBy(detektReportMerge)
        }
    }
}
