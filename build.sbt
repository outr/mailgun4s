name := "mailgun4s"
organization := "org.matthicks"
version := "1.0.13"

scalaVersion := "2.13.0"
crossScalaVersions := List("2.13.0", "2.12.8", "2.11.12")

// Compiler flags
scalacOptions ++= Seq("-deprecation", "-feature")

publishTo in ThisBuild := sonatypePublishTo.value
sonatypeProfileName in ThisBuild := "com.outr"
publishMavenStyle in ThisBuild := true
licenses in ThisBuild := Seq("MIT" -> url("https://github.com/outr/mailgun4s/blob/master/LICENSE"))
sonatypeProjectHosting in ThisBuild := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "mailgun4s", "matt@outr.com"))
homepage in ThisBuild := Some(url("https://github.com/outr/mailgun4s"))
scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/outr/mailgun4s"),
    "scm:git@github.com:outr/mailgun4s.git"
  )
)
developers in ThisBuild := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

fork in run := true

libraryDependencies ++= Seq(
  "io.youi" %% "youi-client" % "0.11.11"
)