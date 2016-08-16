package eu.timepit.datapackage

import eu.timepit.datapackage.testInstances._
import io.circe.{Decoder, Encoder}
import org.scalacheck.Prop._
import org.scalacheck.Properties

class ResourceLocationSpec
    extends Properties(classOf[ResourceLocation].getSimpleName) {
  property("JSON round-trip") = forAll { (location: ResourceLocation) =>
    val json = Encoder[ResourceLocation].apply(location)
    val decoded = Decoder[ResourceLocation].decodeJson(json).toOption.get
    decoded ?= location
  }
}
