package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class ResourceMetadataSpec extends Properties(nameOf[ResourceMetadata]) {
  property("JSON round-trip") = jsonRoundTrip[ResourceMetadata]
  property("JSON decoding error") = jsonDecodeNull[ResourceMetadata]

  property("encodingOrDefault 1") = secure {
    ResourceMetadata().encodingOrDefault ?= "UTF-8"
  }

  property("encodingOrDefault 2") = secure {
    val ascii = "ASCII"
    ResourceMetadata(encoding = Some(ascii)).encodingOrDefault ?= ascii
  }
}
