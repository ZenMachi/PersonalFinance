plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.dokari4.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

val retrofitVersion = rootProject.extra["retrofit_version"]

val roomVersion = rootProject.extra["room_version"]

val rxAndroidVersion = rootProject.extra["rxandroid_version"]
val rxJavaVersion = rootProject.extra["rxjava_version"]
val rxLifecycleVersion = rootProject.extra["rxlifecycle_version"]

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Room
    api ("androidx.room:room-runtime:$roomVersion")
    ksp ("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation ("androidx.room:room-testing:$roomVersion")
    api ("androidx.room:room-ktx:$roomVersion")

    //RxJava
    api ("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
    implementation ("io.reactivex.rxjava2:rxandroid:$rxAndroidVersion")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation ("androidx.room:room-rxjava2:$roomVersion")
    //noinspection GradleDependency
    api ("androidx.lifecycle:lifecycle-reactivestreams-ktx:$rxLifecycleVersion")

    // DataStore
    api("androidx.datastore:datastore-preferences:1.1.1")

    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.1")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")
}