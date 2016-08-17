package eu.timepit.datapackage

import io.circe.syntax._
import io.circe.{Decoder, Encoder}

final case class ResourceInformation(location: ResourceLocation,
                                     metadata: ResourceMetadata)

object ResourceInformation {
  implicit val resourceInformationDecoder: Decoder[ResourceInformation] =
    for {
      location <- Decoder[ResourceLocation]
      metadata <- Decoder[ResourceMetadata]
    } yield ResourceInformation(location, metadata)

  implicit val resourceInformationEncoder: Encoder[ResourceInformation] =
    Encoder.instance { res =>
      res.metadata.asJson.deepMerge(res.location.asJson)
    }
}
