plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.navSafeArgs)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.tanimul.android_template_kotlin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tanimul.android_template_kotlin"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Lifecycle components
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.common.java8)

    // Coroutines
    api(libs.coroutines.core)
    api(libs.coroutines.android)
    implementation(libs.coroutines.core)

    //Shimmer Effect
    implementation(libs.shimmer)

    // Circular Imageview
    implementation(libs.circleimageview)

    // Glide
    implementation(libs.glide.library)
    annotationProcessor(libs.glide.compiler)

    //Lottie animation
    implementation(libs.lottie)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Gson
    implementation(libs.gson)

    //Room for local data storage
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    // Logging with Timber
    implementation(libs.timber)

    // Dependency Injection with Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.fragment)

    // Data storage and preferences
    implementation(libs.androidx.datastore.preferences)

    // Avatar image generator from name
    implementation (libs.avatarx)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.rxjava3)
    implementation (libs.rxandroid)
    implementation (libs.rxjava3.retrofit.adapter)
}