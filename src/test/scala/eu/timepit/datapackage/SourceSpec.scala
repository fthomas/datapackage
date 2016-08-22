package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import eu.timepit.datapackage.util.nameOf
import org.scalacheck.Properties

class SourceSpec extends Properties(nameOf[Source]) {
  property("JSON round-trip") = jsonRoundTrip[Source]
  property("JSON decoding error") = jsonDecodeNull[Source]
}
