package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import org.scalacheck.Properties

class ResourceLocationSpec
    extends Properties(classOf[ResourceLocation].getSimpleName) {
  property("JSON round-trip") = jsonRoundTrip[ResourceLocation]
}
