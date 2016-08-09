package eu.timepit.datapackage

import cats.data.Xor
import io.circe.parser.decode
import io.circe.syntax._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class ResourceInformationSpec
    extends Properties(classOf[ResourceInformation].getSimpleName) {
  {
    val res = ResourceInformation()
    val json = """{}"""

    property("encode minimal resource") = secure {
      res.asJson.noSpaces ?= json
    }

    property("decode minimal resource") = secure {
      decode[ResourceInformation](json) ?= Xor.right(res)
    }
  }
}
