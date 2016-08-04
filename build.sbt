name := "scala-dp"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined" % "0.5.0"
)

initialCommands += """
  import eu.timepit.datapackage._
  import eu.timepit.refined.auto._
"""

reformatOnCompileSettings
