package dev.afilippov.android.drawablegenerator

import org.gradle.work.ChangeType
import org.gradle.work.FileChange
import java.io.File
import java.nio.file.Files
import com.android.ide.common.vectordrawable.Svg2Vector
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.nio.charset.Charset

class VectorDrawableGenerator(catalog: Catalog, outputDir: File) : Generator(catalog, outputDir) {
    override val extension = ".xml"

    override fun processFile(change: FileChange) {
        if (!change.normalizedPath.endsWith(originalExtension)) return
        println("${change.changeType}: ${change.normalizedPath}")
        val outputDir = File(outputDir, "drawable-anydpi")
        val outputFile = File(
            outputDir,
            outputFileName(change.normalizedPath.replace(originalExtension, extension))
        )
        println(outputFile)
        if (change.changeType == ChangeType.REMOVED) {
            outputFile.delete()
        } else {
            Files.createDirectories(outputDir.toPath())
            try {
                val outputStream = ByteArrayOutputStream()
                Svg2Vector.parseSvgToXml(change.file, outputStream)
                outputStream.flush()

                var output = String(outputStream.toByteArray())
                catalog.colorReplacements.map {
                    it to output.indexesOf(it.fromColor, true)
                }.reversed().toMap().forEach { (replacement, indexes) ->
                    indexes.forEach { index ->
                        output = output.replaceRange(index, index + replacement.fromColor.length, replacement.toColor)
                    }
                }
                outputFile.createNewFile()
                outputFile.writeBytes(output.toByteArray())
            } catch (exception: Exception) {

            }
        }
    }
}