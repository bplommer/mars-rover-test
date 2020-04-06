name := "Mars Rover Test"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined" % "0.9.13",
  "eu.timepit" %% "refined-scalacheck" % "0.9.13",
  "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  "org.scalatestplus" %% "scalacheck-1-14" % "3.1.0.0",
  "com.ironcorelabs" %% "cats-scalatest" % "3.0.5" % Test,
)

addCompilerPlugin(
  "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full
)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
