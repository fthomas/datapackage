package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import io.circe.{Decoder, Json}
import org.scalacheck.Prop._
import org.scalacheck.Properties

class AuthorSpec extends Properties(nameOf[Author]) {
  property("JSON round-trip") = jsonRoundTrip[Author]
  property("JSON decoding error") = jsonDecodeNull[Author]

  property("decode from inline string") = forAll { (a: Author) =>
    Decoder[Author].decodeJson(Json.fromString(a.inline)) ?= Right(a)
  }
}
