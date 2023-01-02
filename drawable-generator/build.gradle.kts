plugins {
    kotlin("jvm") version "1.8.0"
    id("com.gradle.plugin-publish") version "1.0.0"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("com.android.tools:sdk-common:30.3.1")
    implementation("org.apache.xmlgraphics:xmlgraphics-commons:2.7")
    implementation("org.apache.xmlgraphics:batik-svggen:1.14")
    implementation("org.apache.xmlgraphics:batik-transcoder:1.14")
    implementation("org.apache.xmlgraphics:batik-codec:1.14")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

group = "dev.afilippov.android"
version = "0.1.0"

gradlePlugin {
    plugins {
        create("android-drawable-generator") {
            id = "dev.afilippov.android.drawablegenerator"
            implementationClass = "dev.afilippov.android.drawablegenerator.DrawableGeneratorPlugin"
            displayName = "Drawable Generator"
        }
    }
}

pluginBundle {
    website = "https://github.com/niveuseverto/android-drawable-generator"
    vcsUrl = "https://github.com/niveuseverto/android-drawable-generator"
    description = "Android gradle plugin to generate tinted vector drawables and png from svg resources"
    tags = listOf("android", "android vector", "svg", "android resources")
}