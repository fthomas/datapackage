name := "datapackage"
licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion := "2.11.8"
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard"
)
scalacOptions in (Compile, console) -= "-Ywarn-unused-import"
scalacOptions in (Test, console) -= "-Ywarn-unused-import"

val circeVersion = "0.4.1"
val refinedVersion = "0.5.0"
val scalacheckVersion = "1.12.6"

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined" % refinedVersion,
  "eu.timepit" %% "refined-scalacheck" % refinedVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-refined" % circeVersion,
  "ai.x" %% "diff" % "1.2.0" % "test",
  "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
)

initialCommands += """
  import eu.timepit.datapackage._
  import eu.timepit.refined.auto._
  import io.circe.{Decoder, Encoder}
  import io.circe.parser
  import io.circe.syntax._
"""

initialCommands in Test += """
  import eu.timepit.datapackage.testUtil._
  import org.scalacheck.Arbitrary
"""

reformatOnCompileSettings

addCommandAlias("validate", ";clean;coverage;test;coverageReport;coverageOff")
