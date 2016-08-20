package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class HashSpec extends Properties(nameOf[Hash]) {
  property("JSON round-trip") = jsonRoundTrip[Hash]
  property("JSON decoding errors") =
    jsonDecodeNull[Hash] && jsonDecodeEmptyString[Hash]
}
