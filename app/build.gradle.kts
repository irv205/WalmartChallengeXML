plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.irv205.walmartchallengexml"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.irv205.walmartchallengexml"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    dependencies {
        // Hilt
        implementation("com.google.dagger:hilt-android:2.51.1")
        kapt("com.google.dagger:hilt-android-compiler:2.51.1")

        // Glide
        implementation("com.github.bumptech.glide:glide:4.15.0")

        // Network & GSON
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.okhttp3:okhttp:4.10.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")

        // KTX
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
        implementation("androidx.activity:activity-ktx:1.6.1")

        // Refresh
        implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

        //Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

        // Room components
        implementation("androidx.room:room-runtime:2.6.1")
        kapt("androidx.room:room-compiler:2.6.1")

        // Room KTX for coroutines support
        implementation("androidx.room:room-ktx:2.6.1")
    }

}

kapt {
    correctErrorTypes
}
