package org.matthicks.mailgun

import fabric._
import fabric.define.DefType
import fabric.rw._

import java.io.File

case class Attachment(file: File, contentType: String)

object Attachment {
  implicit val fileRW: RW[File] = RW.from(
    r = _.getAbsolutePath,
    w = v => new File(v.asStr.value),
    d = DefType.Str
  )

  implicit val rw: RW[Attachment] = RW.gen
}