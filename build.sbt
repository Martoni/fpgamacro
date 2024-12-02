// See README.md for license details.
val majorChiselVersion = "6"
val minorChiselVersion = "2.0"

val chiselVersion = majorChiselVersion + "." + minorChiselVersion

scalaVersion     := "2.13.12"
version          := majorChiselVersion + ".2.2"
organization     := "Martoni"

credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "_",
  System.getenv("GITHUB_TOKEN")
)

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

publishTo := Some("GitHub Martoni Apache Maven Packages" at "https://maven.pkg.github.com/Martoni/fpgamacro")
publishMavenStyle := true
