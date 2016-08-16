package eu.timepit.datapackage

import eu.timepit.datapackage.ResourceLocation.{Data, Path, Url}
import org.scalacheck.{Arbitrary, Gen}

object testInstances {
  implicit val arbitraryResourceLocation: Arbitrary[ResourceLocation] = {
    val url = Gen.alphaStr.map(Url.apply)
    val path = Gen.alphaStr.map(Path.apply)
    val data = Gen.const(Data())
    Arbitrary(Gen.oneOf(url, path, data))
  }
}
