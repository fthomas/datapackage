package eu.timepit.datapackage

import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

final case class License(name: String, url: Option[String] = None)

object License {
  private val keyName = "type"
  private val keyUrl = "url"

  implicit val decodeLicense: Decoder[License] =
    Decoder.decodeString.map(License(_)) or
      Decoder.forProduct2(keyName, keyUrl)(License.apply)

  implicit val encodeLicense: Encoder[License] =
    Encoder.instance {
      case License(name, None) =>
        name.asJson
      case License(name, Some(url)) =>
        Json.obj(keyName -> name.asJson, keyUrl -> url.asJson)
    }
}
