package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class ResourceInformationSpec
    extends Properties(classOf[ResourceInformation].getSimpleName) {
  property("JSON round-trip") = jsonRoundTrip[ResourceInformation]
  property("JSON decoding error") = jsonDecodingError[ResourceInformation]
}
