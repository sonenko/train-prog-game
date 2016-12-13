enablePlugins(ScalaJSPlugin)

enablePlugins(WorkbenchPlugin)

name := "Train programming game"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1"
)

scalacOptions ++= List(
  "-feature",
  "-language:postfixOps",
  "-deprecation",
  "-Ywarn-dead-code",
  "-Xfatal-warnings",
  "-unchecked"
)