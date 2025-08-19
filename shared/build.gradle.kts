import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    id("maven-publish")
}

group = "com.example.movieapp"
version = "1.0.7"
kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
        publishLibraryVariants("release", "debug")
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "KMMShared"
            export(projects.core.domain)
            export(projects.feature.movie.domain)
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.data)
            api(projects.core.domain)
            implementation(projects.core.network)
            implementation(projects.core.database)
            implementation(projects.feature.movie.data)
            api(projects.feature.movie.domain)
            implementation(libs.androidx.room.runtime)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
    val xcframeworkPath = layout.buildDirectory.dir("XCFrameworks/shared.xcframework")
    val simulatorFatFrameworkPath = layout.buildDirectory.dir("fatFramework/shared.framework")

    abstract class CreateFatFrameworkTask : DefaultTask() {
        @get:InputDirectory
        abstract val x64Framework: DirectoryProperty

        @get:InputDirectory
        abstract val arm64SimFramework: DirectoryProperty

        @get:OutputDirectory
        abstract val outputFramework: DirectoryProperty

        @get:Inject
        abstract val execOperations: ExecOperations

        @TaskAction
        fun createFatFramework() {
            outputFramework.get().asFile.deleteRecursively()
            outputFramework.get().asFile.mkdirs()

            execOperations.exec {
                commandLine(
                    "cp",
                    "-R",
                    x64Framework.get().asFile.absolutePath,
                    outputFramework.get().asFile.absolutePath
                )
            }

            execOperations.exec {
                commandLine(
                    "lipo", "-create",
                    "${x64Framework.get().asFile.absolutePath}/shared",
                    "${arm64SimFramework.get().asFile.absolutePath}/shared",
                    "-output", "${outputFramework.get().asFile.absolutePath}/shared"
                )
            }
        }
    }

    val createSimulatorFatFramework by tasks.registering(CreateFatFrameworkTask::class) {
        group = "build"
        description = "Create fat framework for simulator"
        dependsOn("linkDebugFrameworkIosX64", "linkDebugFrameworkIosSimulatorArm64")

        x64Framework.set(layout.buildDirectory.dir("bin/iosX64/debugFramework/shared.framework"))
        arm64SimFramework.set(layout.buildDirectory.dir("bin/iosSimulatorArm64/debugFramework/shared.framework"))
        outputFramework.set(simulatorFatFrameworkPath)
    }

    val buildXCFramework by tasks.creating(Exec::class) {
        group = "build"
        description = "Build XCFramework"

        dependsOn("linkDebugFrameworkIosArm64", createSimulatorFatFramework)

        val deviceFramework =
            layout.buildDirectory.file("bin/iosArm64/debugFramework/shared.framework").get().asFile
        val simulatorFramework = simulatorFatFrameworkPath.get().asFile

        doFirst {
            xcframeworkPath.get().asFile.deleteRecursively()
        }

        commandLine(
            "xcodebuild", "-create-xcframework",
            "-framework", deviceFramework.absolutePath,
            "-framework", simulatorFramework.absolutePath,
            "-output", xcframeworkPath.get().asFile.absolutePath
        )
    }

    val createXCFramework by tasks.creating(Zip::class) {
        group = "build"
        description = "Create XCFramework zip"
        dependsOn(buildXCFramework)

        from(xcframeworkPath)
        archiveBaseName.set("KMMShared")
        archiveClassifier.set("xcframework")
        destinationDirectory.set(layout.buildDirectory.dir("distributions"))
    }
}

android {
    namespace = "com.example.movieapp"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        androidTarget()
    }
}
