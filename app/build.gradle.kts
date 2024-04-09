import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.weathertaskapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weathertaskapp"
        minSdk = 29
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val lifecycle_version = "2.7.0"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:lifecycle_version")

    val coroutines = "1.8.0"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")

    //network
    val retrofitCoroutinesVersion = "0.9.2"
    val gsonVersion = "2.10.1"
    val retrofitVersion = "2.11.0"
    val okHttpVersion = "4.9.0"
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutinesVersion")
    implementation ("com.google.code.gson:gson:$gsonVersion")
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okHttpVersion"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    val koinVersion = "3.3.3"
    implementation ("io.insert-koin:koin-android:$koinVersion")

    val navigationVersion = "2.7.7"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ( "androidx.room:room-compiler:2.6.1")

    implementation ("com.google.android.gms:play-services-location:21.2.0")

}