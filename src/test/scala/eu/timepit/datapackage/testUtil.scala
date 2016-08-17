package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.datapackage.ResourceLocation.{Data, Path, Url}
import eu.timepit.refined.api.Refined
import io.circe.{Decoder, Encoder, Json}
import org.scalacheck.Prop._
import org.scalacheck.{Arbitrary, Gen, Prop}

object testUtil {
  implicit val arbitraryResourceLocation: Arbitrary[ResourceLocation] = {
    val url = Gen.alphaStr.map(Url.apply)
    val path = Gen.alphaStr.map(Path.apply)
    val data = Gen.const(Data())
    Arbitrary(Gen.oneOf(url, path, data))
  }

  implicit val arbitraryResourceMetadata: Arbitrary[ResourceMetadata] = {
    val gen = for {
      name <- Arbitrary.arbitrary[Option[String]]
    } yield ResourceMetadata(name)
    Arbitrary(gen)
  }

  implicit val arbitraryResourceInformation: Arbitrary[ResourceInformation] = {
    val gen = for {
      location <- Arbitrary.arbitrary[ResourceLocation]
      metadata <- Arbitrary.arbitrary[ResourceMetadata]
    } yield ResourceInformation(location, metadata)
    Arbitrary(gen)
  }

  implicit val arbitraryDescriptor: Arbitrary[Descriptor] = {
    val gen = for {
      name <- Gen.alphaStr.map(_.toLowerCase) // Arbitrary.arbitrary[Descriptor.NameType]
      resources <- Arbitrary.arbitrary[List[ResourceInformation]]
    } yield Descriptor(Refined.unsafeApply(name), resources)
    Arbitrary(gen)
  }

  def jsonRoundTrip[A: Arbitrary: Decoder: Encoder]: Prop =
    forAll { (a: A) =>
      val json = Encoder[A].apply(a)
      val decoded = Decoder[A].decodeJson(json)
      decoded ?= Xor.Right(a)
    }

  def jsonDecodingError[A: Decoder]: Prop = secure {
    Decoder[A].decodeJson(Json.Null).isLeft
  }
}
