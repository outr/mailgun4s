package org.matthicks.mailgun

import java.io.File
import java.net.URL

import org.http4s.MediaType

case class Attachment(url: URL, mediaType: MediaType)

object Attachment {
  def apply(file: File, mediaType: MediaType): Attachment = Attachment(file.toURI.toURL, mediaType)
}