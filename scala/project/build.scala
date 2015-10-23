import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
//import com.mojolly.scalate.ScalatePlugin._
//import ScalateKeys._

object ScalaBuild extends Build {
  val Organization = "com.github.diwadidu"
  val Name = "scala"
  val Version = "0.0.1"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "2.4.0.RC3"

  lazy val project = Project (
    "scala",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.2.10.v20150310" % "container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
        "org.mongodb" %% "casbah" % "2.7.2",
        "org.json4s" %% "json4s-jackson" % "3.3.0",
        "org.json4s" %% "json4s-mongo" % "3.3.0"
      )
    )
  )
}
