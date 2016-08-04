package eu.timepit.datapackage

import eu.timepit.datapackage.Metadata.NameType
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.AnyOf
import eu.timepit.refined.char.Digit
import eu.timepit.refined.collection.Forall
import eu.timepit.refined.generic.Equal
import eu.timepit.refined.numeric.Interval
import shapeless.{::, HNil}

final case class Metadata(name: NameType,
                          resources: List[Any],
                          license: Option[Any] = None,
                          title: Option[String] = None,
                          description: Option[String] = None,
                          homepage: Option[Any] = None,
                          version: Option[Any] = None,
                          sources: List[Any]   = List.empty,
                          author: Option[Any]  = None,
                          contributors: Any,
                          keywords: List[String] = List.empty,
                          image: Option[String] = None,
                          dataDependencies: Any,
                          schemas: Any)

object Metadata {
  private type LowerCaseLatinLetter = Interval.Closed[W.`'a'`.T, W.`'z'`.T]

  // From the spec: This MUST be lower-case and contain only alphanumeric
  // characters along with “.”, “_” or “-“ characters.
  type NameType =
    String Refined Forall[AnyOf[LowerCaseLatinLetter :: Digit :: Equal[
                W.`'.'`.T] :: Equal[W.`'_'`.T] :: Equal[W.`'-'`.T] :: HNil]]
}
