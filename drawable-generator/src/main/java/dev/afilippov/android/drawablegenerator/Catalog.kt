package dev.afilippov.android.drawablegenerator

import java.io.Serializable

data class Catalog (
     val name: String = "default",
     var path: String,
     var prefix: String = "",
     var outputType: OutputType = OutputType.vectorDrawable,
     var densities: Set<Density> = Density.all,
     val colorReplacements: MutableList<ColorReplacement> = mutableListOf()
): CatalogInterface, Serializable {
     override fun path(path: String) {
          this.path = path
     }

     override fun prefix(prefix: String) {
          this.prefix = prefix
     }

     override fun outputType(outputType: OutputType) {
          this.outputType = outputType
     }

     override fun densities(densities: Set<Density>) {
          this.densities = densities
     }

     fun replaceColor(fromColor: String, toColor:String) {
          colorReplacements.add(ColorReplacement(fromColor, toColor))
     }

     override fun equals(other: Any?): Boolean {
          if (this === other) return true
          if (javaClass != other?.javaClass) return false

          other as Catalog

          if (name != other.name) return false
          if (path != other.path) return false
          if (prefix != other.prefix) return false
          if (outputType != other.outputType) return false
          if (densities != other.densities) return false
          if (colorReplacements != other.colorReplacements) return false

          return true
     }

     override fun hashCode(): Int {
          var result = name.hashCode()
          result = 31 * result + path.hashCode()
          result = 31 * result + prefix.hashCode()
          result = 31 * result + outputType.hashCode()
          result = 31 * result + densities.hashCode()
          result = 31 * result + colorReplacements.hashCode()
          return result
     }
}