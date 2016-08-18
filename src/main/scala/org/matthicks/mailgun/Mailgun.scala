package org.matthicks.mailgun

import org.http4s.{Method, Uri}
import org.http4s.multipart.{Multipart, Part}

import scala.concurrent.Future
import org.http4s._
import org.http4s.client._
import org.http4s.client.blaze.{defaultClient => client}
import org.http4s.headers._
import upickle._

class Mailgun(domain: String, apiKey: String) {
  private val messagesURL = Uri.fromString(s"https://api.mailgun.net/v3/$domain/messages")
    .getOrElse(throw new RuntimeException("Failed to parse URL"))

  def send(message: Message): Future[MessageResponse] = {
    var parts = Vector[Part](
      Part.formData("from", message.from.toString),
      Part.formData("subject", message.subject)
    )
    def add(part: Part): Unit = {
      parts = parts :+ part
    }
    message.to.foreach { to =>
      add(Part.formData("to", to.toString))
    }
    message.cc.foreach { cc =>
      add(Part.formData("cc", cc.toString))
    }
    message.bcc.foreach { bcc =>
      add(Part.formData("bcc", bcc.toString))
    }
    message.tags.foreach { tag =>
      add(Part.formData("o:tag", tag))
    }
    message.campaignId.foreach { campaignId =>
      add(Part.formData("o:campaign", campaignId))
    }
    message.dkim.foreach { dkim =>
      add(Part.formData("o:dkim", if (dkim) "yes" else "no"))
    }
    message.deliveryTime.foreach { deliveryTime =>
      add(Part.formData("o.deliverytime", deliveryTime))
    }
    if (message.testMode) {
      add(Part.formData("o:testmode", "yes"))
    }
    message.tracking.foreach { tracking =>
      add(Part.formData("o:tracking", if (tracking) "yes" else "no"))
    }
    message.trackingClicks match {
      case TrackingClicks.Default => // Nothing needs to be set
      case TrackingClicks.Yes => add(Part.formData("o:tracking-clicks", "yes"))
      case TrackingClicks.No => add(Part.formData("o:tracking-clicks", "no"))
      case TrackingClicks.HTMLOnly => add(Part.formData("o:tracking-clicks", "htmlonly"))
    }
    message.trackingOpens.foreach { trackingOpens =>
      add(Part.formData("o:tracking-opens", if (trackingOpens) "yes" else "no"))
    }
    if (message.requireTLS) {
      add(Part.formData("o:require-tls", "yes"))
    }
    if (message.skipVerification) {
      add(Part.formData("o:skip-verification", "yes"))
    }
    message.customHeaders.foreach {
      case (key, value) => add(Part.formData(s"h:$key", value))
    }
    message.customData.foreach {
      case (key, value) => add(Part.formData(s"v:$key", value))
    }
    message.text.foreach { text =>
      add(Part.formData("text", text))
    }
    message.html.foreach { text =>
      add(Part.formData("html", text))
    }
    message.inline.foreach { attachment =>
      add(Part.fileData("attachment", attachment.url, `Content-Type`(attachment.mediaType)))
    }
    message.inline.foreach { inline =>
      add(Part.fileData("inline", inline.url, `Content-Type`(inline.mediaType)))
    }
    val multipart = Multipart(parts)
    val request = Method.POST(messagesURL, multipart)
      .map(_.replaceAllHeaders(multipart.headers).putHeaders(Authorization(BasicCredentials("api", apiKey))))
    client.expect[String](request).map(default.read[MessageResponse])
  }
}