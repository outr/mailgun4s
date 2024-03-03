name := "mailgun4s"
organization := "com.outr"
version := "1.2.3"

scalaVersion := "2.13.12"
crossScalaVersions := List("2.13.12", "3.3.3")

// Compiler flags
scalacOptions ++= Seq("-deprecation", "-feature")

ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
ThisBuild / sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
ThisBuild / publishTo := sonatypePublishTo.value
ThisBuild / sonatypeProfileName := "com.outr"
ThisBuild / licenses := Seq("MIT" -> url("https://github.com/outr/mailgun4s/blob/master/LICENSE"))
ThisBuild / sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "mailgun4s", "matt@outr.com"))
ThisBuild / homepage := Some(url("https://github.com/outr/mailgun4s"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/outr/mailgun4s"),
    "scm:git@github.com:outr/mailgun4s.git"
  )
)
ThisBuild / developers := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("https://matthicks.com"))
)

run / fork := true

libraryDependencies ++= Seq(
  "com.outr" %% "spice-client-jvm" % "0.4.0"
)