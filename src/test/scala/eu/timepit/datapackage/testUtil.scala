package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.datapackage.ResourceLocation.{Data, Path, Url}
import eu.timepit.refined.api.Refined
import io.circe.{Decoder, Encoder, Json}
import org.scalacheck.Prop._
import org.scalacheck.{Arbitrary, Gen, Prop}
import scala.reflect.ClassTag

object testUtil {
  implicit val arbitraryLicense: Arbitrary[License] = {
    val str = Gen.alphaStr.map(License.Str.apply)
    val obj = for {
      tpe <- Arbitrary.arbitrary[String]
      url <- Arbitrary.arbitrary[String]
    } yield License.Obj(tpe, url)
    Arbitrary(Gen.oneOf(str, obj))
  }

  implicit val arbitraryResourceLocation: Arbitrary[ResourceLocation] = {
    val url = Gen.alphaStr.map(Url.apply)
    val path = Gen.alphaStr.map(Path.apply)
    val data = Gen.const(Data())
    Arbitrary(Gen.oneOf(url, path, data))
  }

  implicit val arbitraryResourceMetadata: Arbitrary[ResourceMetadata] = {
    val gen = for {
      name <- Arbitrary.arbitrary[Option[String]]
      title <- Arbitrary.arbitrary[Option[String]]
      description <- Arbitrary.arbitrary[Option[String]]
      format <- Arbitrary.arbitrary[Option[String]]
      mediatype <- Gen.alphaStr.map(Some.apply)
      encoding <- Gen.alphaStr.map(Some.apply)
    } yield
      ResourceMetadata(name = name,
                       title = title,
                       description = description,
                       format = format,
                       mediatype = mediatype,
                       encoding = encoding)
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
      license <- Arbitrary.arbitrary[Option[License]]
      title <- Arbitrary.arbitrary[Option[String]]
      description <- Arbitrary.arbitrary[Option[String]]
      homepage <- Arbitrary.arbitrary[Option[String]]
      version <- Arbitrary.arbitrary[Option[String]]
      keywords <- Arbitrary.arbitrary[Option[List[String]]]
      image <- Arbitrary.arbitrary[Option[String]]
    } yield
      Descriptor(name = Refined.unsafeApply(name),
                 resources = resources,
                 license = license,
                 title = title,
                 description = description,
                 homepage = homepage,
                 version = version,
                 keywords = keywords,
                 image = image)
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

  def nameOf[T](implicit ct: ClassTag[T]): String =
    ct.runtimeClass.getSimpleName
}
