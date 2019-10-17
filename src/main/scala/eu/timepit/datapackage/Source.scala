package eu.timepit.datapackage

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final case class Source(
    name: Option[String] = None,
    web: Option[String] = None,
    email: Option[String] = None
)

object Source {
  implicit val decodeSource: Decoder[Source] = deriveDecoder

  implicit val encodeSource: Encoder[Source] = deriveEncoder
}
