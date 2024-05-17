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

val roomVersion = rootProject.extra["room_version"]
val retrofitVersion = rootProject.extra["retrofit_version"]
val loggingInterceptorVersion = rootProject.extra["logging_interceptor_version"]

val rxLifecycleVersion = rootProject.extra["rxlifecycle_version"]
val rxAndroidVersion = rootProject.extra["rxandroid_version"]
val rxJavaVersion = rootProject.extra["rxjava_version"]

val fragmentKtxVersion = rootProject.extra["fragment_ktx_version"]
val activityKtxVersion = rootProject.extra["activity_ktx_version"]

val glideVersion = rootProject.extra["glide_version"]
val daggerVersion = rootProject.extra["dagger_version"]
val hiltVersion = rootProject.extra["hilt_version"]

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    //Room
    api ("androidx.room:room-runtime:$roomVersion")
    ksp ("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation ("androidx.room:room-testing:$roomVersion")

    //RxJava
    api ("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
    implementation ("io.reactivex.rxjava2:rxandroid:$rxAndroidVersion")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation ("androidx.room:room-rxjava2:$roomVersion")
    //noinspection GradleDependency
    api ("androidx.lifecycle:lifecycle-reactivestreams-ktx:$rxLifecycleVersion")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.1")

    //noinspection GradleDependency
    api ("androidx.activity:activity-ktx:$activityKtxVersion")
    //noinspection GradleDependency
    api ("androidx.fragment:fragment-ktx:$fragmentKtxVersion")

    implementation ("com.github.bumptech.glide:glide:$glideVersion")

    //Dagger
    implementation ("com.google.dagger:dagger:$daggerVersion")
    kapt ("com.google.dagger:dagger-compiler:$daggerVersion")

    //Hilt
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    kapt ("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}