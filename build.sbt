import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

workbenchSettings

name := "Train programming game"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.0"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1"
)

bootSnippet := "train.Main();"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
