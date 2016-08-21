package eu.timepit.datapackage

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

final case class Author(name: String,
                        web: Option[String] = None,
                        email: Option[String] = None)

object Author {
  implicit val decodeAuthor: Decoder[Author] = deriveDecoder

  implicit val encodeAuthor: Encoder[Author] = deriveEncoder
}
