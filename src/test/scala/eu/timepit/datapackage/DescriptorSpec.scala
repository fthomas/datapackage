package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class DescriptorSpec extends Properties(classOf[Descriptor].getSimpleName) {
  property("JSON round-trip") = jsonRoundTrip[Descriptor]
  property("JSON decoding error") = jsonDecodingError[Descriptor]
}
