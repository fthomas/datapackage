package eu.timepit.datapackage
package examples

import eu.timepit.refined.auto._
import io.circe.parser
import org.scalacheck.Prop._
import org.scalacheck.Properties

class Gdp extends Properties("examples.gdp") {

  val datapackage: String = {
    val path = "/datasets/gdp/datapackage.json"
    val is = getClass.getResourceAsStream(path)
    scala.io.Source.fromInputStream(is).mkString
  }

  property("datapackage.json") = secure {
    val resource =
      ResourceInformation(ResourceLocation.Path("data/gdp.csv"),
                          ResourceMetadata(name = Some("gdp")))

    val sources = List(
      Source(name = Some("World Bank and OECD"),
             web = Some("http://data.worldbank.org/indicator/NY.GDP.MKTP.CD")))

    val descriptor = Descriptor(
      name = "gdp",
      resources = List(resource),
      license = Some(License("PDDL-1.0")),
      title = Some("Country, Regional and World GDP (Gross Domestic Product)"),
      description = Some(
        "Country, regional and world GDP in current US Dollars ($). Regional means collections of countries e.g. Europe & Central Asia. Data is sourced from the World Bank and turned into a standard normalized CSV."),
      version = Some("2011"),
      sources = Some(sources),
      keywords =
        Some(List("GDP", "World", "Gross Domestic Product", "Time series")),
      image =
        Some("http://assets.okfn.org/p/opendatahandbook/img/data-wrench.png"))

    parser.decode[Descriptor](datapackage) ?= Right(descriptor)
  }
}
