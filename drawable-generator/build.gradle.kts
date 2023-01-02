plugins {
    kotlin("jvm") version "1.8.0"
    `java-gradle-plugin`
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

gradlePlugin {
    plugins {
        create("android-drawable-generator") {
            id = "dev.afilippov.android.drawablegenerator"
            implementationClass = "dev.afilippov.android.drawablegenerator.DrawableGeneratorPlugin"
            version = "0.1.0"
            displayName = "Drawable Generator"
        }
    }
}

