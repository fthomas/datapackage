package eu.timepit.datapackage

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

final case class Author(name: String,
                        web: Option[String] = None,
                        email: Option[String] = None) {

  def inline: String =
    (name :: List(email.map(e => s"<$e>"), web.map(w => s"($w)")).flatten)
      .mkString(" ")
}

object Author {
  implicit val decodeAuthor: Decoder[Author] = deriveDecoder

  implicit val encodeAuthor: Encoder[Author] = deriveEncoder
}
