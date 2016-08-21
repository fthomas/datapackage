package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class AuthorSpec extends Properties(nameOf[Author]) {
  property("JSON round-trip") = jsonRoundTrip[Author]
  property("JSON decoding error") = jsonDecodeNull[Author]
}
