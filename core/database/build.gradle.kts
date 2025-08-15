plugins {
    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    kotlin("plugin.serialization") version "2.2.0"
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.androidx.room)
    id("maven-publish")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
    }
    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
//    androidLibrary {
//        namespace = "com.example.share.core.database"
//        compileSdk = 35
//        minSdk = 24
//
//        withHostTestBuilder {
//
//        }
//
//        withDeviceTestBuilder {
//            sourceSetTreeName = "test"
//        }.configure {
//            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        }
//    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "database"
            isStatic = true
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.androidx.sqlite.bundled)
                implementation(libs.androidx.room.runtime)
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.delight.android.driver)
            }
        }

//        getByName("androidDeviceTest") {
//            dependencies {
//                implementation(libs.androidx.runner)
//                implementation(libs.androidx.core)
//                implementation(libs.androidx.junit)
//            }
//        }

        iosMain {
            dependencies {
                implementation(libs.delight.native.driver)
            }
        }
    }
}
dependencies{
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
android {
    namespace = "com.example.share.core.database"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin{
        androidTarget()
    }
}
