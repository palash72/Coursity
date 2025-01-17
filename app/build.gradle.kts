plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")

}

android {
    namespace = "com.example.course"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.course"
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


}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-database:20.3.1")


    implementation ("androidx.media3:media3-exoplayer:1.2.1")
    implementation ("androidx.media3:media3-exoplayer-dash:1.2.1")
    implementation ("androidx.media3:media3-ui:1.2.1")


    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    implementation ("com.github.kotvertolet:youtube-jextractor:0.3.4")
    implementation ("com.github.HaarigerHarald:android-youtubeExtractor:2.1.0")

    implementation ("com.android.support:support-annotations:28.0.0")
    implementation ("com.github.evgenyneu:js-evaluator-for-android:v6.0.0")


    implementation ("org.aspectj:aspectjrt:1.8.13")
    implementation ("com.googlecode.mp4parser:isoparser:1.1.22")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")




}