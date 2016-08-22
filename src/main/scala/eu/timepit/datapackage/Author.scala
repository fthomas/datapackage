package eu.timepit.datapackage

import cats.data.Xor
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
    deriveDecoder[Author] or Decoder.instance { c =>
      val Pattern1 = raw"""(?s)(.*) <(.*)> \((.*)\)""".r
      val Pattern2 = raw"""(?s)(.*) <(.*)>""".r
      val Pattern3 = raw"""(?s)(.*) \((.*)\)""".r
      Decoder.decodeString.apply(c).flatMap {
        case Pattern1(name, email, web) =>
          Xor.Right(Author(name, Some(web), Some(email)))
        case Pattern2(name, email) =>
          Xor.Right(Author(name, None, Some(email)))
        case Pattern3(name, web) =>
          Xor.Right(Author(name, Some(web), None))
        case name =>
          Xor.Right(Author(name))
      }
    }

  implicit val encodeAuthor: Encoder[Author] = deriveEncoder
}
