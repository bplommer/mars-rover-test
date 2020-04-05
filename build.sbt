name := "Mars Rover Test"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "eu.timepit" %% "refined"                 % "0.9.13"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
