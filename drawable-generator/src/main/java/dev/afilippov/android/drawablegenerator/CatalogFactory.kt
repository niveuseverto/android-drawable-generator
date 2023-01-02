package dev.afilippov.android.drawablegenerator

import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project

class CatalogFactory(private val project: Project) : NamedDomainObjectFactory<Catalog> {
    override fun create(name: String): Catalog {
        project.logger.error("Create catalog: $name")
        return Catalog(name = name, path = name)
    }
}