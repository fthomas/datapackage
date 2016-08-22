package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.datapackage.types.NonEmptyString
import eu.timepit.refined.api.RefType
import eu.timepit.refined.auto._
import io.circe.{Decoder, Encoder, Json}

final case class Hash(digest: NonEmptyString,
                      algorithm: Option[NonEmptyString] = None)

object Hash {
  implicit val decodeHash: Decoder[Hash] =
    Decoder.decodeString.emap { str =>
      val parts = str.split(":", 2).toList
      val nonEmptyParts =
        parts.flatMap(RefType.applyRef[NonEmptyString](_).right.toOption)
      nonEmptyParts match {
        case digest :: Nil =>
          Xor.Right(Hash(digest))
        case algorithm :: digest :: Nil =>
          Xor.Right(Hash(digest, Some(algorithm)))
        case _ =>
          Xor.Left(s"Hash: invalid data: $str")
      }
    }

  implicit val encodeHash: Encoder[Hash] =
    Encoder.instance { hash =>
      val algorithm = hash.algorithm.fold("")(_ + ":")
      Json.fromString(algorithm + hash.digest)
    }
}
