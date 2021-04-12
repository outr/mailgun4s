name := "mailgun4s"
organization := "org.matthicks"
version := "1.0.15-SNAPSHOT"

scalaVersion := "2.13.5"
crossScalaVersions := List("2.13.5", "2.12.13")

// Compiler flags
scalacOptions ++= Seq("-deprecation", "-feature")

ThisBuild / publishTo := sonatypePublishTo.value
ThisBuild / sonatypeProfileName := "com.outr"
ThisBuild / publishMavenStyle := true
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
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

run / fork := true

libraryDependencies ++= Seq(
  "io.youi" %% "youi-client" % "0.14.0"
)