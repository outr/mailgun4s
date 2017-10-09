name := "mailgun4s"
organization := "org.matthicks"
version := "1.0.7-SNAPSHOT"

scalaVersion := "2.12.3"
crossScalaVersions := List("2.12.3", "2.11.11")

// Compiler flags
scalacOptions ++= Seq("-deprecation", "-feature")

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeRepo("releases")
)

fork in run := true

libraryDependencies ++= Seq(
  "io.youi" %% "youi-client" % "0.7.2"
)

pomExtra := <url>http://matthicks.org</url>
  <licenses>
    <license>
      <name>MIT License</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <developerConnection>scm:https://github.com/outr/mailgun4s.git</developerConnection>
    <connection>scm:https://github.com/outr/mailgun4s.git</connection>
    <url>https://github.com/outr/mailgun4s</url>
  </scm>
  <developers>
    <developer>
      <id>darkfrog26</id>
      <name>Matt Hicks</name>
      <url>http://www.matthicks.com</url>
    </developer>
  </developers>