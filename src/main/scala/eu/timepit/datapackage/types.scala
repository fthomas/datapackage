package eu.timepit.datapackage

import eu.timepit.refined._
import eu.timepit.refined.boolean.Or
import eu.timepit.refined.generic.Equal
import eu.timepit.refined.numeric.Interval
import shapeless.{::, HNil}

object types {
  type LatinLetter = LowerCaseLatinLetter Or UpperCaseLatinLetter

  type LowerCaseLatinLetter = Interval.Closed[W.`'a'`.T, W.`'z'`.T]

  type UpperCaseLatinLetter = Interval.Closed[W.`'A'`.T, W.`'Z'`.T]

  type DotDashUnderscore =
    Equal[W.`'.'`.T] :: Equal[W.`'-'`.T] :: Equal[W.`'_'`.T] :: HNil
}
