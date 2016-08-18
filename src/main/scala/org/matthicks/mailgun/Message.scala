package org.matthicks.mailgun

import java.io.File
import java.net.URL

import org.http4s.MediaType

case class Message(from: EmailAddress,
                   to: List[EmailAddress] = Nil,
                   subject: String,
                   cc: List[EmailAddress] = Nil,
                   bcc: List[EmailAddress] = Nil,
                   text: Option[String] = None,
                   html: Option[String] = None,
                   attachments: List[Attachment] = Nil,
                   inline: List[Attachment] = Nil,
                   tags: List[String] = Nil,
                   campaignId: Option[String] = None,
                   dkim: Option[Boolean] = None,
                   deliveryTime: Option[String] = None,
                   testMode: Boolean = false,
                   tracking: Option[Boolean] = None,
                   trackingClicks: TrackingClicks = TrackingClicks.Default,
                   trackingOpens: Option[Boolean] = None,
                   requireTLS: Boolean = false,
                   skipVerification: Boolean = false,
                   customHeaders: Map[String, String] = Map.empty,
                   customData: Map[String, String] = Map.empty) {
  def withInline(url: URL, mediaType: MediaType): Message = {
    copy(inline = Attachment(url, mediaType) :: inline)
  }
  def withInline(file: File, mediaType: MediaType): Message = {
    copy(inline = Attachment(file, mediaType) :: inline)
  }
}

object Message {
  def simple(from: EmailAddress,
             to: EmailAddress,
             subject: String,
             text: String = null,
             html: String = null): Message = {
    Message(from = from, to = List(to), subject = subject, text = Option(text), html = Option(html))
  }
}