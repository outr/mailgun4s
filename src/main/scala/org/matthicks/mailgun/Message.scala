package org.matthicks.mailgun

import java.io.File
import java.net.URL

case class Message(from: EmailAddress,
                   to: List[EmailAddress] = Nil,
                   subject: String,
                   cc: List[EmailAddress] = Nil,
                   bcc: List[EmailAddress] = Nil,
                   template: Option[String] = None,
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
  def withInline(file: File, contentType: String): Message = {
    copy(inline = Attachment(file, contentType) :: inline)
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