package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.datapackage.ResourceLocation.Url
import io.circe.Printer
import io.circe.parser.decode
import io.circe.syntax._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class ResourceInformationSpec
    extends Properties(classOf[ResourceInformation].getSimpleName) {
  {
    val res = ResourceInformation(Url("test"))
    val json = """{"url":"test"}"""

    property("encode minimal resource") = secure {
      res.asJson.pretty(Printer.noSpaces.copy(dropNullKeys = true)) ?= json
    }

    property("decode minimal resource") = secure {
      decode[ResourceInformation](json) ?= Xor.right(res)
    }
  }

  {
    val res = ResourceInformation(Url("test"), Some("data"))
    val json = """{"url":"test","name":"data"}"""

    property("encode resource with name") = secure {
      res.asJson.pretty(Printer.noSpaces.copy(dropNullKeys = true)) ?= json
    }

    property("decode resource with name") = secure {
      decode[ResourceInformation](json) ?= Xor.right(res)
    }
  }
}
