package dev.afilippov.android.drawablegenerator

import org.apache.batik.transcoder.SVGAbstractTranscoder
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.w3c.dom.Document
import java.io.File

class Svg2Png {
    companion object {
        fun renderSvgToPng(document: Document, outputs: Map<Density, File>) {
            val size = Size(
                document.documentElement.attributes.getNamedItem("width").nodeValue.toFloat(),
                document.documentElement.attributes.getNamedItem("height").nodeValue.toFloat()
            )

            val ti = TranscoderInput(document)
            val t = PNGTranscoder()

            // Disable XXE
            t.addTranscodingHint(SVGAbstractTranscoder.KEY_ALLOW_EXTERNAL_RESOURCES, false)
            // https://github.com/sterlp/svg2png/issues/11
            t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, true)

            outputs.forEach { (density, outputFile) ->
                t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, size.width * density.ratio)
                t.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, size.height * density.ratio)
                outputFile.parentFile.mkdirs()
                t.transcode(ti, TranscoderOutput(outputFile.outputStream()))
            }
        }
    }
}