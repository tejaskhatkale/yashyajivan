// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories {
        google()
    }

    dependencies{
        val navVersion = "2.8.4"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}