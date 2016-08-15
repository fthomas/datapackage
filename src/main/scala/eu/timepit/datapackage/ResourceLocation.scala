package eu.timepit.datapackage

import io.circe.{Decoder, Encoder, Json}

sealed trait ResourceLocation extends Product with Serializable

object ResourceLocation {
  final case class Url(value: String) extends ResourceLocation
  final case class Path(value: String) extends ResourceLocation
  final case class Data() extends ResourceLocation

  implicit val resourceLocationDecoder: Decoder[ResourceLocation] =
    Decoder.instance { c =>
      val x = c.downField("url").cursor.map(_.as[String].map(Url.apply))
      x.get
    }

  implicit val resourceLocationEncoder: Encoder[ResourceLocation] =
    Encoder.instance {
      case Url(value) => Json.obj("url" -> Json.fromString(value))
      case Path(value) => Json.obj("path" -> Json.fromString(value))
      case Data() => Json.obj("data" -> Json.Null)
    }
}
