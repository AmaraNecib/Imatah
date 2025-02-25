plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.plugin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.serialization)
    //alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.imatah"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.imatah"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation("io.github.huhx:compose-image-picker:1.0.8")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")






    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // ViewModel Kotlin extensions for easier ViewModel usage
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Compose integration for ViewModel to use ViewModel in composable functions
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose integration with Activity for setting up Compose UI in Activities
    implementation(libs.androidx.activity.compose)
    // Coil image loading library for Compose to load and display images efficiently
    implementation(libs.io.coil.kt.compose)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Navigation compose
    implementation(libs.androidx.navigation.compose)


    //firebase

//    implementation(platform(libs.google.firebase.bom))
//    implementation(libs.google.firebase.analytics.ktx)
//    implementation(libs.google.firebase.auth.ktx)
//    implementation(libs.google.firebase.firestore.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


}