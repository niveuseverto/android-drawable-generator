package dev.afilippov.android.drawablegenerator

enum class Density(val ratio: Float) {
    ldpi(0.75f),
    mdpi(1.0f),
    hdpi(1.5f),
    xhdpi(2.0f),
    xxhdpi(3.0f),
    xxxhdpi(4.0f);

    companion object {
        @JvmStatic
        val all = setOf(ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
    }
}