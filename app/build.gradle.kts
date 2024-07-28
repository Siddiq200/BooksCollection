plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.orgJetbrainsKotlinKapt)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safeargs.kotlin)
}

android {
    namespace = "com.app.bookscollection"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.bookscollection"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //Paging
    implementation(libs.androidx.paging)

    //Local Db
    implementation(libs.androidx.room)
    kapt(libs.androidx.room.compiler)

    //DI
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    //Navigation
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)

    //Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.rxjava)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    //Rx
    implementation(libs.rx.android)

    //Image Loading
    implementation(libs.glide)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}