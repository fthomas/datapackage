package eu.timepit.datapackage

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

final case class ResourceMetadata(name: Option[String] = None, // TODO: refine!
                                  title: Option[String] = None,
                                  description: Option[String] = None,
                                  format: Option[String] = None)

object ResourceMetadata {
  implicit val decoderResourceMetadata: Decoder[ResourceMetadata] =
    deriveDecoder

  implicit val encoderResourceMetadata: Encoder[ResourceMetadata] =
    deriveEncoder
}
