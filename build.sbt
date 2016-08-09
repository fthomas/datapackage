name := "datapackage"

scalaVersion := "2.11.8"

val circeVersion = "0.4.1"
val refinedVersion = "0.5.0"

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined" % refinedVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-refined" % circeVersion,
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
)

initialCommands += """
  import eu.timepit.datapackage._
  import eu.timepit.refined.auto._
"""

reformatOnCompileSettings
