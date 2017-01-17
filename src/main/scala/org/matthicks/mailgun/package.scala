package org.matthicks

import scala.concurrent.{Future, Promise}
import scala.language.implicitConversions
import scalaz.concurrent.Task

package object mailgun {
  implicit def task2Future[T](t: Task[T]): Future[T] = {
    val p = Promise[T]()

    import scalaz.{\/-, -\/}

    t.runAsync {
      case -\/(ex) => {
        p.failure(ex)
        ()
      }
      case \/-(r) => {
        p.success(r)
        ()
      }
    }
    p.future
  }
}