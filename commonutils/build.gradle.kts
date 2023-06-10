import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("github.properties")))

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.postliu"
                artifactId = "common_utils"
                version = "1.0.1.5"
                from(components["release"])
            }
        }
    }
}

android {
    namespace = "com.postliu.commonutils"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

dependencies {
    api("io.coil-kt:coil:2.2.2")
    api("com.google.code.gson:gson:2.10")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    api("com.github.DylanCaiCoding.ViewBindingKTX:viewbinding-ktx:2.1.0")
    api("com.github.DylanCaiCoding.ViewBindingKTX:viewbinding-base:2.1.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
