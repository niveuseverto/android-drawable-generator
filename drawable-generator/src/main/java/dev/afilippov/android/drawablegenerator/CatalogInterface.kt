package dev.afilippov.android.drawablegenerator

import java.io.File

interface CatalogInterface {
    fun path(path: String)
    fun prefix(prefix: String)
    fun outputType(outputType: OutputType)
    fun densities(vararg density: Density) = densities(setOf(*density))
    fun densities(densities: Set<Density>)
}