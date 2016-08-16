package eu.timepit.datapackage

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder, Json}

final case class ResourceMetadata()

final case class ResourceInformation(location: ResourceLocation,
                                     name: Option[String] = None)

object ResourceInformation {
  implicit val resourceInformationDecoder: Decoder[ResourceInformation] =
    Decoder.instance { c =>
      for {
        loc <- Decoder[ResourceLocation].apply(c)
        name <- c.downField("name").as[Option[String]]
      } yield ResourceInformation(loc, name)
    }

  implicit val resourceInformationEncoder: Encoder[ResourceInformation] =
    deriveEncoder[ResourceInformation].mapJson { x =>
      val o = x.asObject.get
      val loc = o.apply("location").get
      val o2 = o.remove("location")

      Json.fromJsonObject(o2).deepMerge(loc)
    }
}
