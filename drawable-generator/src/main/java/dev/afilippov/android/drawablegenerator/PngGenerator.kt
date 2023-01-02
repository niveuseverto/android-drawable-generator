package dev.afilippov.android.drawablegenerator

import org.gradle.work.ChangeType
import org.gradle.work.FileChange
import java.io.ByteArrayInputStream
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class PngGenerator(catalog: Catalog, outputDir: File) : Generator(catalog, outputDir) {
    override val extension = ".png"

    override fun processFile(change: FileChange) {
        if (!change.normalizedPath.endsWith(originalExtension)) return
        println("${change.changeType}: ${change.normalizedPath}")
        val outputs = catalog.densities.associateWith { density ->
            File(
                File(outputDir, "drawable-$density"),
                outputFileName(change.normalizedPath.replace(originalExtension, extension))
            )
        }
        if (change.changeType == ChangeType.REMOVED) {
            outputs.values.forEach {
                it.delete()
            }
        } else {
            var input = change.file.readText()
            catalog.colorReplacements.map {
                it to input.indexesOf(it.fromColor, true)
            }.reversed().toMap().forEach { (replacement, indexes) ->
                indexes.forEach { index ->
                    input = input.replaceRange(
                        index,
                        index + replacement.fromColor.length,
                        replacement.toColor
                    )
                }
            }
            val builderFactory = DocumentBuilderFactory.newInstance()
            builderFactory.isNamespaceAware = true
            val builder = builderFactory.newDocumentBuilder()
            val document = builder.parse(ByteArrayInputStream(input.toByteArray()))
            Svg2Png.renderSvgToPng(document, outputs)
        }
    }
}