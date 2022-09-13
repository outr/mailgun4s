[![Codacy Badge](https://api.codacy.com/project/badge/Grade/be6b2c52582c469ca0092cde7893b909)](https://www.codacy.com/app/matthicks/mailgun4s?utm_source=github.com&utm_medium=referral&utm_content=outr/mailgun4s&utm_campaign=badger)
[![Build Status](https://secure.travis-ci.org/outr/mailgun4s.png?branch=master)](http://travis-ci.org/outr/mailgun4s)

mailgun4s 
============

Updated project originally forked from https://github.com/Nycto/mailgun-scala but since has been completely re-written.

A Scala wrapper around the Mailgun API. Their documentation can
be found here:

http://documentation.mailgun.com

Currently only supports sending messages, but more functionality will be added as needed or requested.

Example
-------

Note that as of 1.1, we now use [cats-effect](https://typelevel.org/cats-effect/) instead of Futures.

The following example shows how to instantiate and send an email:

```scala
import org.matthicks.mailgun._
import java.io._
import scala.concurrent._
import cats.effect.IO

val mailgun = new Mailgun("samples.mailgun.org", "key-YOUR-MAILGUN-KEY")
val io: IO[MessageResponse] = mailgun.send(Message.simple(
  from = EmailAddress("nobody@example.com", "Test App"),
  to = List(EmailAddress("mailgun-scala@mailinator.com", "Joe User")),
  subject = "Mailgun4s Rules!",
  text = Some("This is the testing text"),
  html = Some("<html><b>This</b> <i>seems</i> <img src=\"cid:example.jpg\"/> to <h1>work!</h1></html>")
).withInline(new File("example.jpg"), "image/jpeg"))

io.map { result =>
  println(s"Result: $result")
}
```

Adding it to your Project
-------------------------

Add the following directives to your `build.sbt` file:

```
libraryDependencies ++= Seq(
    "org.matthicks" %% "mailgun4s" % "1.1.0"
)
```

License
-------

This library is released under the MIT License, which is pretty spiffy. You
should have received a copy of the MIT License along with this program. If not,
see <http://www.opensource.org/licenses/mit-license.php>.