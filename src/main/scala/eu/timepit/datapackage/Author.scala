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
  implicit val decodeAuthor: Decoder[Author] =
    deriveDecoder[Author] or Decoder.decodeString.map { str =>
      val Pattern1 = raw"""(?s)(.*) <(.*)> \((.*)\)""".r
      val Pattern2 = raw"""(?s)(.*) <(.*)>""".r
      val Pattern3 = raw"""(?s)(.*) \((.*)\)""".r
      str match {
        case Pattern1(name, email, web) =>
          Author(name, Some(web), Some(email))
        case Pattern2(name, email) =>
          Author(name, None, Some(email))
        case Pattern3(name, web) =>
          Author(name, Some(web), None)
        case name =>
          Author(name)
      }
    }

  implicit val encodeAuthor: Encoder[Author] = deriveEncoder
}
