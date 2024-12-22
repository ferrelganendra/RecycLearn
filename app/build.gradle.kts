plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}


android {
    namespace = "com.example.recyclearn"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.recyclearn"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation (platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation ("com.google.firebase:firebase-firestore:24.9.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database")
//    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.firebase.storage.ktx)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    kapt ("com.github.bumptech.glide:compiler:4.14.2")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.30")

    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // Coroutines for async operations
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
