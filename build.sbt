name := "mailgun4s"

organization := "org.matthicks"

version := "1.0.0"

scalaVersion := "2.11.8"

sbtVersion := "0.13.11"

// Compiler flags
scalacOptions ++= Seq("-deprecation", "-feature")

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeRepo("releases")
)

libraryDependencies ++= Seq(
  "com.outr.scribe" %% "scribe-slf4j" % "1.2.3",
  "org.http4s" %% "http4s-blaze-client" % "0.14.2a",
  "com.lihaoyi" %% "upickle" % "0.4.1"
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