package dev.afilippov.android.drawablegenerator

import com.android.build.gradle.BaseExtension;
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.internal.reflect.Instantiator
import java.io.File
import javax.inject.Inject

class DrawableGeneratorPlugin @Inject constructor(private val instantiator: Instantiator) :
    Plugin<Project> {

    private lateinit var extension: DrawableGeneratorExtension
    private lateinit var outputDir: File

    override fun apply(project: Project) {
        val main = project.extensions.findByType(BaseExtension::class.java)!!
            .sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)

        outputDir = File(project.buildDir, "generated/drawable-generator")

        val srcDirs = main.resources.srcDirs.toMutableSet().apply {
            add(outputDir)
        }
        main.resources.setSrcDirs(srcDirs)

        val catalogsContainer = project.container(Catalog::class.java, CatalogFactory(project))

        extension = project.extensions.create(
            extensionName,
            DrawableGeneratorExtension::class.java,
            catalogsContainer
        )

        project.afterEvaluate {
            onAfterEvaluate(it)
        }
    }

    private fun onAfterEvaluate(project: Project) {


        extension.catalogs().values.forEach { catalog ->
            val task = project.tasks.register(
                "generateDrawables${catalog.name.capitalized()}",
                DrawableGeneratorTask::class.java
            ) {
                it.input.set(catalog)
                it.inputDir.set(File(project.projectDir, catalog.path))
                it.outputDir.set(outputDir)
            }
            project.tasks.getByName("preBuild").dependsOn(task)
        }
    }

    companion object {
        private const val extensionName = "drawableGenerator"
    }
}