package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.datapackage.ResourceLocation.{Data, Path, Url}
import eu.timepit.datapackage.types.Natural
import eu.timepit.refined.api.Refined
import eu.timepit.refined.scalacheck.numeric._
import io.circe.{Decoder, Encoder, Json}
import org.scalacheck.Prop._
import org.scalacheck.{Arbitrary, Gen, Prop}
import scala.reflect.ClassTag

object testUtil {
  implicit val arbitraryAuthor: Arbitrary[Author] = {
    val gen = for {
      name <- Arbitrary.arbitrary[String]
      web <- Arbitrary.arbitrary[Option[String]]
      email <- Arbitrary.arbitrary[Option[String]]
    } yield Author(name, web, email)
    Arbitrary(gen)
  }

  implicit val arbitraryHash: Arbitrary[Hash] = {
    val gen = for {
      digest <- Gen.alphaStr.filter(_.nonEmpty)
      algorithm <- Gen.option(Gen.alphaStr.filter(_.nonEmpty))
    } yield
      Hash(Refined.unsafeApply(digest), algorithm.map(Refined.unsafeApply))
    Arbitrary(gen)
  }

  implicit val arbitraryLicense: Arbitrary[License] = {
    val gen = for {
      name <- Arbitrary.arbitrary[String]
      url <- Arbitrary.arbitrary[Option[String]]
    } yield License(name, url)
    Arbitrary(gen)
  }

  implicit val arbitrarySource: Arbitrary[Source] = {
    val gen = for {
      name <- Arbitrary.arbitrary[Option[String]]
      web <- Arbitrary.arbitrary[Option[String]]
      email <- Arbitrary.arbitrary[Option[String]]
    } yield Source(name, web, email)
    Arbitrary(gen)
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
      bytes <- Arbitrary.arbitrary[Option[Natural]]
      hash <- Arbitrary.arbitrary[Option[Hash]]
      license <- Arbitrary.arbitrary[Option[License]]
    } yield
      ResourceMetadata(name = name,
                       title = title,
                       description = description,
                       format = format,
                       mediatype = mediatype,
                       encoding = encoding,
                       bytes = bytes,
                       hash = hash,
                       license = license)
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
      sources <- Arbitrary.arbitrary[Option[List[Source]]]
      author <- Arbitrary.arbitrary[Option[Author]]
      contributors <- Arbitrary.arbitrary[Option[List[Author]]]
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
                 sources = sources,
                 author = author,
                 contributors = contributors,
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

  def jsonDecodeNull[A: Decoder]: Prop = secure {
    Decoder[A].decodeJson(Json.Null).isLeft
  }

  def jsonDecodeEmptyString[A: Decoder]: Prop = secure {
    Decoder[A].decodeJson(Json.fromString("")).isLeft
  }

  def nameOf[T](implicit ct: ClassTag[T]): String =
    ct.runtimeClass.getSimpleName
}
