package org.matthicks.mailgun

import gigahorse.support.asynchttpclient.Gigahorse

import scala.concurrent.Future
import upickle._

class Mailgun(domain: String, apiKey: String) {
  private val messagesURL = s"https://api.mailgun.net/v3/$domain/messages"

  def send(message: Message): Future[MessageResponse] = {
    var parts = Map.empty[String, List[String]]
    def add(key: String, value: Any): Unit = {
      parts += key -> List(value.toString)
    }
    add("from", message.from)
    add("subject", message.subject)
    message.to.foreach { to =>
      add("to", to)
    }
    message.cc.foreach { cc =>
      add("cc", cc)
    }
    message.bcc.foreach { bcc =>
      add("bcc", bcc)
    }
    message.tags.foreach { tag =>
      add("o:tag", tag)
    }
    message.campaignId.foreach { campaignId =>
      add("o:campaign", campaignId)
    }
    message.dkim.foreach { dkim =>
      add("o:dkim", if (dkim) "yes" else "no")
    }
    message.deliveryTime.foreach { deliveryTime =>
      add("o.deliverytime", deliveryTime)
    }
    if (message.testMode) {
      add("o:testmode", "yes")
    }
    message.tracking.foreach { tracking =>
      add("o:tracking", if (tracking) "yes" else "no")
    }
    message.trackingClicks match {
      case TrackingClicks.Default => // Nothing needs to be set
      case TrackingClicks.Yes => add("o:tracking-clicks", "yes")
      case TrackingClicks.No => add("o:tracking-clicks", "no")
      case TrackingClicks.HTMLOnly => add("o:tracking-clicks", "htmlonly")
    }
    message.trackingOpens.foreach { trackingOpens =>
      add("o:tracking-opens", if (trackingOpens) "yes" else "no")
    }
    if (message.requireTLS) {
      add("o:require-tls", "yes")
    }
    if (message.skipVerification) {
      add("o:skip-verification", "yes")
    }
    message.customHeaders.foreach {
      case (key, value) => add(s"h:$key", value)
    }
    message.customData.foreach {
      case (key, value) => add(s"v:$key", value)
    }
    message.text.foreach { text =>
      add("text", text)
    }
    message.html.foreach { text =>
      add("html", text)
    }
    message.inline.foreach { attachment =>
//      add(Part.fileData("attachment", attachment.url, `Content-Type`(attachment.mediaType)))
      throw new UnsupportedOperationException("Attachments not currently supported!")
    }
    message.inline.foreach { inline =>
//      add(Part.fileData("inline", inline.url, `Content-Type`(inline.mediaType)))
      throw new UnsupportedOperationException("Inline Attachments not currently supported!")
    }

    val client = Gigahorse.http(Gigahorse.config)
    try {
      val request = Gigahorse
        .url(messagesURL)
        .post(parts)
        .withAuth("api", apiKey)
      client.run(request, Gigahorse.asString.andThen(default.read[MessageResponse]))
    } catch {
      case t: Throwable => {
        client.close()
        throw t
      }
    }
  }
}