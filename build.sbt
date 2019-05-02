organization  := "mike.test"

version       := "0.1"

scalaVersion  := "2.12.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  "io.circe"      %% "circe-core"    % "0.11.1",
  "io.circe"      %% "circe-generic" % "0.11.1",
  "io.circe"      %% "circe-parser"  % "0.11.1",
  "org.scalatest" %% "scalatest"     % "3.0.7"  % "test"
  )
