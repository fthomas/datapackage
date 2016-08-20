package eu.timepit.datapackage

import io.circe.syntax._
import io.circe.{Decoder, Encoder}

final case class ResourceInformation(location: ResourceLocation,
                                     metadata: ResourceMetadata)

object ResourceInformation {
  implicit val decodeResourceInformation: Decoder[ResourceInformation] =
    for {
      location <- Decoder[ResourceLocation]
      metadata <- Decoder[ResourceMetadata]
    } yield ResourceInformation(location, metadata)

  implicit val encodeResourceInformation: Encoder[ResourceInformation] =
    Encoder.instance { res =>
      res.metadata.asJson.deepMerge(res.location.asJson)
    }
}
