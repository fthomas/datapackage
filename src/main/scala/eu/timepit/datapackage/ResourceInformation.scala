package eu.timepit.datapackage

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final case class ResourceInformation()

object ResourceInformation {
  implicit val resourceInformationDecoder: Decoder[ResourceInformation] =
    deriveDecoder

  implicit val resourceInformationEncoder: Encoder[ResourceInformation] =
    deriveEncoder
}
