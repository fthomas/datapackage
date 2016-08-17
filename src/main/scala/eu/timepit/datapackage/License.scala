package eu.timepit.datapackage

import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

sealed trait License extends Product with Serializable

object License {
  final case class Str(tpe: String) extends License
  final case class Obj(tpe: String, url: String) extends License

  private val keyTpe = "type"
  private val keyUrl = "url"

  implicit val decoderLicense: Decoder[License] =
    Decoder.instance { c =>
      def str = c.as[String].map(Str.apply)
      def obj =
        for {
          tpe <- c.downField(keyTpe).as[String]
          url <- c.downField(keyUrl).as[String]
        } yield Obj(tpe, url)
      str.orElse(obj)
    }

  implicit val encoderLicense: Encoder[License] =
    Encoder.instance {
      case Str(tpe) => tpe.asJson
      case Obj(tpe, url) =>
        Json.obj(keyTpe -> tpe.asJson, keyUrl -> url.asJson)
    }
}
