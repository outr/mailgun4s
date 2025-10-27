package org.matthicks.mailgun

import fabric.rw._

import java.io.File

case class Attachment(file: File, contentType: String)

object Attachment {
  implicit val fileRW: RW[File] = RW.string(
    asString = _.getAbsolutePath,
    fromString = s => new File(s)
  )

  implicit val rw: RW[Attachment] = RW.gen
}