package eu.timepit.datapackage
package examples

import cats.data.Xor
import eu.timepit.refined.auto._
import io.circe.parser
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scala.io.Source

class WorldCities extends Properties("examples.world-cities") {

  val datapackage: String = {
    val path = "/datasets/world-cities/datapackage.json"
    val is = getClass.getResourceAsStream(path)
    Source.fromInputStream(is).mkString
  }

  property("datapackage.json") = secure {
    val resource = ResourceInformation(
      ResourceLocation.Path("data/world-cities.csv"),
      ResourceMetadata(name = Some("world-cities"), format = Some("csv")))

    val descriptor = Descriptor(name = "world-cities",
                                resources = List(resource),
                                title = Some("Major cities of the world"))

    parser.decode[Descriptor](datapackage) ?= Xor.Right(descriptor)
  }
}
