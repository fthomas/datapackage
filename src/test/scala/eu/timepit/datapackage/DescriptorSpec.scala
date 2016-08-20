package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class DescriptorSpec extends Properties(nameOf[Descriptor]) {
  property("JSON round-trip") = jsonRoundTrip[Descriptor]
  property("JSON decoding error") = jsonDecodeNull[Descriptor]
}
