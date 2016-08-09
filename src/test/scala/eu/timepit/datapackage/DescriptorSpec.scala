package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.refined.auto._
import io.circe.parser.decode
import io.circe.syntax._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class DescriptorSpec extends Properties(classOf[Descriptor].getSimpleName) {
  {
    val dp = Descriptor(name = "test.dp", resources = List.empty)
    val json = """{"name":"test.dp","resources":[]}"""

    property("encode minimal descriptor") = secure {
      dp.asJson.noSpaces ?= json
    }

    property("decode minimal descriptor") = secure {
      decode[Descriptor](json) ?= Xor.Right(dp)
    }
  }
}
