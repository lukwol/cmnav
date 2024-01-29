import BuildConstants.Modules.Namespace

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm("desktop")

    androidTarget {
        publishLibraryVariants("release")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.animation)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.compose.navigation.android)
                implementation(libs.kotlin.serialization.json)
            }
        }
        create("nonAndroidMain") {
            dependsOn(getByName("commonMain"))
        }
        getByName("desktopMain") {
            dependsOn(getByName("nonAndroidMain"))
        }
        iosMain {
            dependsOn(getByName("nonAndroidMain"))
        }
        getByName("androidInstrumentedTest").dependencies {
            implementation(kotlin("test"))
            implementation(compose.material3)
            implementation(libs.kotlin.serialization.json)
            implementation(libs.test.runner.android)
            implementation(libs.compose.ui.test.junit4)
        }
        getByName("desktopTest").dependencies {
            implementation(kotlin("test"))
            implementation(compose.material3)
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.uiTestJUnit4)
        }
    }
}

android {
    compileSdk = BuildConstants.Android.CompileSdk
    namespace = BuildConstants.Modules.NavigationScreens.Namespace

    defaultConfig {
        minSdk = BuildConstants.Android.MinSdk
        testInstrumentationRunner = BuildConstants.Android.TestInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = BuildConstants.JavaVersion
        targetCompatibility = BuildConstants.JavaVersion
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    dependencies {
        debugImplementation(libs.compose.ui.test.manifest.android)
    }
}
