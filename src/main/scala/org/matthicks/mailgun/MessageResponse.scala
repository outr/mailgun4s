package org.matthicks.mailgun

import fabric.rw._

case class MessageResponse(id: String, message: String)

object MessageResponse {
  implicit val rw: RW[MessageResponse] = RW.gen
}