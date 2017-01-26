package org.matthicks.mailgun

import java.io.File
import java.net.URL

case class Attachment(url: URL, contentType: String)

object Attachment {
  def apply(file: File, contentType: String): Attachment = Attachment(file.toURI.toURL, contentType)
}