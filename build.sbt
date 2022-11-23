// See README.md for license details.

scalaVersion     := "2.13.8"
version          := "0.2.1-SNAPSHOT"
organization     := "Martoni"

githubOwner := "Martoni"
githubRepository := "fpgamacro"

lazy val root = (project in file("."))
  .settings(
    name := "fpgamacro",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % "3.5.4",
      "edu.berkeley.cs" %% "chiseltest" % "0.5.4" % "test"
    ),
    scalacOptions ++= Seq(
      "-Xsource:2.11",
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit"
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.1" cross CrossVersion.full),
  )
