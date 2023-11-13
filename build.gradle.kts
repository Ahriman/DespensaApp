// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("com.android.library") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
//    kotlin("kapt") version "1.9.20"
//    kotlin("jvm") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}

tasks.register(name = "clean", type = Delete::class) {
//    delete(rootProject.buildDir)
    delete(layout.buildDirectory)
}


//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
