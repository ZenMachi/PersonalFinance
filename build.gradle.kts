// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id ("com.android.library") version "8.0.0" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}

buildscript {

    extra.apply{
        set("glide_version", "4.14.2")
        set("room_version", "2.5.2")
        set("retrofit_version", "2.9.0")
        set("logging_interceptor_version", "4.8.0")
        set("kotlin_version", "1.4.20")

        set("lifecycle_version" , "2.6.1")

        set("rxjava_version" , "2.2.19")
        set("rxandroid_version" , "2.1.1")
        set("rxlifecycle_version" , "2.2.0")

        set("dagger_version" , "2.50")

        set ("activity_ktx_version" , "1.1.0")
        set ("fragment_ktx_version" , "1.2.5")
        set ("hilt_version" , "2.50")
    }
    repositories {
        google()
    }

}