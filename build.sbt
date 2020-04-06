name := "Mars Rover Test"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "co.fs2" %% "fs2-core" % "2.2.1",
  "co.fs2" %% "fs2-io" % "2.2.1",
  "eu.timepit" %% "refined" % "0.9.13",
  "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  "com.ironcorelabs" %% "cats-scalatest" % "3.0.5" % Test,
)

addCompilerPlugin(
  "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full
)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
