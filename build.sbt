// See README.md for license details.

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.2.1-SNAPSHOT"

lazy val publishSettings = Seq (
    // groupId, SCM, license information
    organization     := "io.github.Martoni",
    homepage := Some(url("https://github.com/Martoni/fpgamacro")),
    scmInfo := Some(ScmInfo(url("https://github.com/Martoni/fpgamacro"),
                            "git@github.com/Martoni/fpgamacro")),
    developers := List(Developer("Martoni", "Martoni",
                                 "mail@fabienm.eu",
                                 url("https://github.com/Martoni"))),
    licenses += ("Unlicense", url("https://unlicense.org/")),
    publishMavenStyle := true,
    
    // disable publish with Scala version
    crossPaths := false,
    publishTo := Some(
        if (isSnapshot.value)
            Opts.resolver.sonatypeSnapshots
        else
            Opts.resolver.sonatypeStaging
        ),
)

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
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.4" cross CrossVersion.full),
  ).settings(publishSettings: _*)
