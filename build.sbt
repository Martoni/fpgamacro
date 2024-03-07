// See README.md for license details.

scalaVersion     := "2.13.12"
version          := "6.1.0"
organization     := "Martoni"

val majorChiselVersion = "6"
val minorChiselVersion = "1.0"

val chiselVersion = majorChiselVersion + "." + minorChiselVersion

lazy val root = (project in file("."))
  .settings(
    name := "fpgamacro",
    libraryDependencies ++= Seq(
      "org.chipsalliance" %% "chisel" % chiselVersion,
      "org.scalatest" %% "scalatest" % "3.2.16" % "test",
    ),
    scalacOptions ++= Seq(
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-Ymacro-annotations",
    ),
    addCompilerPlugin("org.chipsalliance" % "chisel-plugin" % chiselVersion cross CrossVersion.full),
  )
