package eu.timepit.datapackage
package examples

import cats.data.Xor
import eu.timepit.refined.auto._
import io.circe.parser
import org.scalacheck.Prop._
import org.scalacheck.Properties

class WorldCities extends Properties("examples.world-cities") {

  val datapackage: String = {
    val path = "/datasets/world-cities/datapackage.json"
    val is = getClass.getResourceAsStream(path)
    scala.io.Source.fromInputStream(is).mkString
  }

  property("datapackage.json") = secure {
    val resource =
      ResourceInformation(ResourceLocation.Path("data/world-cities.csv"),
                          ResourceMetadata(name = Some("world-cities"),
                                           format = Some("csv"),
                                           mediatype = Some("text/csv")))

    val license =
      License.Obj("ODC-PDDL", "http://opendatacommons.org/licenses/pddl/1.0/")

    val sources = List(
      Source(name = Some("Geonames"), web = Some("http://www.geonames.org/")))

    val descriptor = Descriptor(
      name = "world-cities",
      resources = List(resource),
      license = Some(license),
      title = Some("Major cities of the world"),
      description =
        Some("List of the world's major cities (above 15,000 inhabitants)"),
      homepage = Some("http://github.com/datasets/world-cities"),
      sources = Some(sources),
      keywords = Some(List("geodata", "city")))

    parser.decode[Descriptor](datapackage) ?= Xor.Right(descriptor)
  }
}
