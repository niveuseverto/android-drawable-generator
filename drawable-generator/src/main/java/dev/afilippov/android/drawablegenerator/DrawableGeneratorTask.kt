package dev.afilippov.android.drawablegenerator

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

abstract class DrawableGeneratorTask : DefaultTask() {
    @get:Input
    abstract val input: Property<Catalog>

    @get:Incremental
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputDirectory
    abstract val inputDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun execute(inputChanges: InputChanges) {
        val catalog = input.get()
        val generator = when (catalog.outputType) {
            OutputType.vectorDrawable -> VectorDrawableGenerator(catalog, outputDir.get().asFile)
            OutputType.png -> PngGenerator(catalog, outputDir.get().asFile)
        }

        inputChanges.getFileChanges(inputDir).forEach { change ->
            generator.processFile(change)
        }
    }
}
