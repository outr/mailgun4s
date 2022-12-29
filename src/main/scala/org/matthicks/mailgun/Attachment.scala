package org.matthicks.mailgun

import fabric._
import fabric.define.DefType
import fabric.rw._

import java.io.File

case class Attachment(file: File, contentType: String)

object Attachment {
  implicit val fileRW: RW[File] = RW.from(_.getAbsolutePath, v => new File(v.asStr.value), DefType.Str)

  implicit val rw: RW[Attachment] = RW.gen
}