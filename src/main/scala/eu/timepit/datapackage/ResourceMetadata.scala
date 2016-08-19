package eu.timepit.datapackage

import eu.timepit.datapackage.types.Natural
import io.circe.generic.semiauto._
import io.circe.refined._
import io.circe.{Decoder, Encoder}

final case class ResourceMetadata(name: Option[String] = None, // TODO: refine!
                                  title: Option[String] = None,
                                  description: Option[String] = None,
                                  format: Option[String] = None,
                                  mediatype: Option[String] = None,
                                  encoding: Option[String] = None,
                                  bytes: Option[Natural] = None) {

  def encodingOrDefault: String =
    encoding.getOrElse("UTF-8")
}

object ResourceMetadata {
  implicit val decoderResourceMetadata: Decoder[ResourceMetadata] =
    deriveDecoder

  implicit val encoderResourceMetadata: Encoder[ResourceMetadata] =
    deriveEncoder
}
