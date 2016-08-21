package eu.timepit.datapackage

import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

final case class License(name: String, url: Option[String] = None)

object License {
  private val keyName = "type"
  private val keyUrl = "url"

  implicit val decodeLicense: Decoder[License] =
    Decoder.instance { c =>
      def str = c.as[String].map(License(_))
      def obj =
        for {
          name <- c.downField(keyName).as[String]
          url <- c.downField(keyUrl).as[String]
        } yield License(name, Some(url))
      str.orElse(obj)
    }

  implicit val encodeLicense: Encoder[License] =
    Encoder.instance {
      case License(name, None) =>
        name.asJson
      case License(name, Some(url)) =>
        Json.obj(keyName -> name.asJson, keyUrl -> url.asJson)
    }
}
