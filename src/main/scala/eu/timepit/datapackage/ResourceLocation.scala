package eu.timepit.datapackage

import cats.implicits._
import eu.timepit.datapackage.util.JsonKey
import eu.timepit.datapackage.util.JsonKey.keyOf
import io.circe._
import io.circe.syntax._

sealed trait ResourceLocation extends Product with Serializable

object ResourceLocation {
  final case class Url(value: String) extends ResourceLocation
  final case class Path(value: String) extends ResourceLocation
  final case class Data() extends ResourceLocation

  implicit val decodeResourceLocation: Decoder[ResourceLocation] =
    Decoder.instance { c =>
      def url = c.downField(keyOf[Url]).as[String].right.map(Url.apply)
      def path = c.downField(keyOf[Path]).as[String].right.map(Path.apply)
      def data = c.downField(keyOf[Data]).as[Unit].right.map(_ => Data())
      url.orElse(path).orElse(data)
    }

  implicit val encodeResourceLocation: Encoder[ResourceLocation] =
    Encoder.instance {
      case Url(value)  => Json.obj(keyOf[Url] -> value.asJson)
      case Path(value) => Json.obj(keyOf[Path] -> value.asJson)
      case Data()      => Json.obj(keyOf[Data] -> Json.Null)
    }

  implicit val jsonKeyUrl: JsonKey[Url] = JsonKey.instance("url")
  implicit val jsonKeyPath: JsonKey[Path] = JsonKey.instance("path")
  implicit val jsonKeyData: JsonKey[Data] = JsonKey.instance("data")
}
