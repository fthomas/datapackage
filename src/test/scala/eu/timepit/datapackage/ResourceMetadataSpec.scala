package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class ResourceMetadataSpec extends Properties(nameOf[ResourceMetadata]) {
  property("JSON round-trip") = jsonRoundTrip[ResourceMetadata]
  property("JSON decoding error") = jsonDecodeNull[ResourceMetadata]
}
