package dev.afilippov.android.drawablegenerator

import org.gradle.work.FileChange
import java.io.File

abstract class Generator(protected val catalog: Catalog, protected val outputDir: File) {
    protected val originalExtension = ".svg"

    abstract val extension: String

    abstract fun processFile(change: FileChange)

    protected fun outputFileName(normalizedPath: String): String {
        var outputFile = normalizedPath
        if (catalog.prefix.isNotEmpty()) {
            outputFile = "${catalog.prefix}/$normalizedPath"
        }
        return outputFile.replace('/', '_')
    }

    fun ignoreCaseOpt(ignoreCase: Boolean) =
        if (ignoreCase) setOf(RegexOption.IGNORE_CASE) else emptySet()

    fun String?.indexesOf(pat: String, ignoreCase: Boolean = true): List<Int> =
        pat.toRegex(ignoreCaseOpt(ignoreCase))
            .findAll(this?: "")
            .map { it.range.first }
            .toList()
}