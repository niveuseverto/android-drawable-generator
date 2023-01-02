package dev.afilippov.android.drawablegenerator

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project
import java.io.File
import javax.inject.Inject

abstract class DrawableGeneratorExtension(
    private val project: Project,
    private val catalogContainer: NamedDomainObjectContainer<Catalog>
) : CatalogInterface {
    private val objects = project.objects
    private val logger = project.logger
    private val projectDir = project.projectDir

    private val defaultCatalog = Catalog(path = defaultPath)
    private val catalogs = mutableMapOf<String, Catalog>()

    override fun path(path: String) {
        defaultCatalog.path = path
    }

    override fun prefix(prefix: String) {
        defaultCatalog.prefix = prefix
    }

    override fun outputType(outputType: OutputType) {
        defaultCatalog.outputType = outputType
    }

    override fun densities(densities: Set<Density>) {
        defaultCatalog.densities = densities
    }

    fun replaceColor(fromColor: String, toColor:String) {
        defaultCatalog.colorReplacements.add(ColorReplacement(fromColor, toColor))
    }

    fun additionalCatalogs(action: Action<NamedDomainObjectContainer<Catalog>>) {
        action.execute(catalogContainer)
    }

    fun catalogs(): Map<String, Catalog> {
        return mapOf(
            defaultCatalog.name to defaultCatalog
        ) + catalogContainer.map {
            it.name to it
        }
    }

    companion object {
        private const val defaultPath = "svg"
    }
}