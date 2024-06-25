plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

apply(from = "../shared_dependencies.gradle")



android {
    namespace = "com.dokari4.personalfinance"
    compileSdk = 34

    sourceSets {
        named("main") {
            res.srcDir("src/main/res")
            res.srcDir("src/main/res/layouts/accounts")
            res.srcDir("src/main/res/layouts/home")
            res.srcDir("src/main/res/layouts/overview")
        }
    }

    defaultConfig {
        applicationId = "com.dokari4.personalfinance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {

        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

val loggingInterceptorVersion = rootProject.extra["logging_interceptor_version"]


val fragmentKtxVersion = rootProject.extra["fragment_ktx_version"]
val activityKtxVersion = rootProject.extra["activity_ktx_version"]

val glideVersion = rootProject.extra["glide_version"]
val daggerVersion = rootProject.extra["dagger_version"]
val hiltVersion = rootProject.extra["hilt_version"]

dependencies {
    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.core:core-splashscreen:1.0.0")
}