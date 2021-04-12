package org.matthicks.mailgun

import fabric.rw._

import java.io.File
import java.net.URL

case class Attachment(file: File, contentType: String)

object Attachment {
  implicit val fileRW: ReaderWriter[File] = ReaderWriter(_.getAbsolutePath, v => new File(v.asStr.value))

  implicit val rw: ReaderWriter[Attachment] = ccRW
}